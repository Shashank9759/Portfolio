(function () {
  "use strict";

  function clamp(v, a, b) { return Math.max(a, Math.min(b, v)); }

  function hexToRgb(hex) {
    if (!hex || hex[0] !== "#") return { r: 120, g: 180, b: 255 };
    var c = hex.slice(1);
    if (c.length === 3) c = c[0] + c[0] + c[1] + c[1] + c[2] + c[2];
    var n = parseInt(c, 16);
    return { r: (n >> 16) & 255, g: (n >> 8) & 255, b: n & 255 };
  }

  function rgba(c, a) {
    if (typeof c === "string") c = hexToRgb(c);
    return "rgba(" + c.r + "," + c.g + "," + c.b + "," + a + ")";
  }

  function cloneVec(v) { return v.slice(); }

  function rotatePlane(v, i, j, angle) {
    var c = Math.cos(angle), s = Math.sin(angle);
    var vi = v[i] * c - v[j] * s;
    var vj = v[i] * s + v[j] * c;
    v[i] = vi;
    v[j] = vj;
  }

  function buildHypercube(dims, size) {
    var count = 1 << dims;
    var verts = [];
    var i, d;
    for (i = 0; i < count; i += 1) {
      var v = [];
      for (d = 0; d < dims; d += 1) v.push((i & (1 << d)) ? size : -size);
      verts.push(v);
    }
    var edges = [];
    for (i = 0; i < count; i += 1) {
      for (var b = i + 1; b < count; b += 1) {
        var diff = 0;
        for (d = 0; d < dims; d += 1) if (verts[i][d] !== verts[b][d]) diff += 1;
        if (diff === 1) edges.push([i, b]);
      }
    }
    return { dims: dims, verts: verts, edges: edges };
  }

  function buildHyperspherePoints(dims, count, radius) {
    var pts = [];
    var i, d;
    for (i = 0; i < count; i += 1) {
      var v = [];
      var len = 0;
      for (d = 0; d < dims; d += 1) {
        var val = Math.random() * 2 - 1;
        v.push(val);
        len += val * val;
      }
      len = Math.sqrt(len) || 1;
      for (d = 0; d < dims; d += 1) v[d] = (v[d] / len) * radius;
      pts.push(v);
    }
    return pts;
  }

  function animateND(v, now, seed, speedMul) {
    var out = cloneVec(v);
    var dims = out.length;
    var p, a, b;
    for (p = 0; p < dims - 1; p += 1) {
      for (a = p + 1; a < dims; a += 1) {
        var ang = now * (0.00014 + p * 0.00003 + a * 0.00002) * speedMul + seed * 0.7 + p * 1.1 + a * 0.4;
        rotatePlane(out, p, a, ang);
      }
    }
    return out;
  }

  function projectND(v, cx, cy, scale, now) {
    var depth = 2.8;
    var d;
    for (d = 2; d < v.length; d += 1) {
      depth += v[d] * (0.11 + Math.sin(now * 0.0011 + d * 1.7) * 0.04);
    }
    var w = v.length > 3 ? v[v.length - 1] * 0.06 : 0;
    var persp = 1 / (depth + w + 0.5);
    return {
      x: cx + v[0] * scale * persp,
      y: cy + v[1] * scale * persp,
      depth: depth,
      alpha: clamp(0.12 + persp * 0.78, 0.05, 0.95),
    };
  }

  var cube5 = buildHypercube(5, 1);
  var cube6 = buildHypercube(6, 1);
  var sphere10 = buildHyperspherePoints(10, 96, 1);

  function drawHyperObject(ctx, obj, cx, cy, scale, now, color, energy, isEdges) {
    var speed = 1 + energy * 2.8;
    var projected = [];
    var i;

    if (isEdges) {
      for (i = 0; i < obj.verts.length; i += 1) {
        projected.push(projectND(animateND(obj.verts[i], now, i * 0.13, speed), cx, cy, scale, now));
      }
      ctx.save();
      ctx.globalCompositeOperation = "screen";
      for (i = 0; i < obj.edges.length; i += 1) {
        var e = obj.edges[i];
        var p1 = projected[e[0]], p2 = projected[e[1]];
        var depth = (p1.depth + p2.depth) * 0.5;
        ctx.strokeStyle = rgba(color, (0.04 + energy * 0.12) * ((p1.alpha + p2.alpha) * 0.5));
        ctx.lineWidth = 0.6 + energy * 1.2;
        ctx.beginPath();
        ctx.moveTo(p1.x, p1.y);
        ctx.lineTo(p2.x, p2.y);
        ctx.stroke();
      }
      ctx.restore();
    } else {
      var pts = obj;
      for (i = 0; i < pts.length; i += 1) {
        projected.push(projectND(animateND(pts[i], now, i * 0.09, speed * 1.1), cx, cy, scale, now));
      }
      ctx.save();
      ctx.globalCompositeOperation = "screen";
      for (i = 0; i < projected.length; i += 1) {
        for (var j = i + 1; j < projected.length; j += 1) {
          if ((i + j) % 7 !== 0 && (i + j) % 11 !== 0) continue;
          var a = projected[i], b = projected[j];
          var dist = Math.hypot(a.x - b.x, a.y - b.y);
          if (dist > scale * 1.8) continue;
          ctx.strokeStyle = rgba(color, 0.03 + energy * 0.08);
          ctx.lineWidth = 0.5;
          ctx.beginPath();
          ctx.moveTo(a.x, a.y);
          ctx.lineTo(b.x, b.y);
          ctx.stroke();
        }
        var p = projected[i];
        ctx.fillStyle = rgba(color, p.alpha * (0.25 + energy * 0.45));
        ctx.beginPath();
        ctx.arc(p.x, p.y, 1 + energy * 1.5, 0, Math.PI * 2);
        ctx.fill();
      }
      ctx.restore();
    }
  }

  function drawCalabiYau(ctx, cx, cy, r, now, color, energy) {
    var layers = 14 + Math.floor(energy * 10);
    var i, t;
    ctx.save();
    ctx.globalCompositeOperation = "screen";
    for (i = 0; i < layers; i += 1) {
      t = i / layers;
      var ang = now * (0.00035 + t * 0.0002) * (1 + energy * 2);
      var rad = r * (0.35 + t * 0.95);
      var wobble = Math.sin(now * 0.002 + i * 0.8) * r * 0.08 * (1 + energy);
      ctx.strokeStyle = rgba(color, (0.05 + energy * 0.1) * (1 - t * 0.6));
      ctx.lineWidth = 0.8 + energy;
      ctx.beginPath();
      for (var s = 0; s <= 64; s += 1) {
        var a = (s / 64) * Math.PI * 2;
        var x = cx + Math.cos(a + ang) * (rad + wobble * Math.sin(a * 3 + i));
        var y = cy + Math.sin(a * 1.3 + ang * 0.7) * (rad * 0.72 + wobble * Math.cos(a * 2));
        if (s === 0) ctx.moveTo(x, y); else ctx.lineTo(x, y);
      }
      ctx.stroke();
    }
    ctx.restore();
  }

  function drawQuantumFoam(ctx, w, h, now, colors, energy) {
    var n = Math.floor(40 + energy * 90);
    var i;
    ctx.save();
    ctx.globalCompositeOperation = "lighter";
    for (i = 0; i < n; i += 1) {
      var seed = i * 17.13;
      var x = (Math.sin(now * 0.0004 + seed) * 0.5 + 0.5) * w;
      var y = (Math.cos(now * 0.00035 + seed * 1.3) * 0.5 + 0.5) * h;
      var col = i % 3 === 0 ? colors.primary : i % 3 === 1 ? colors.secondary : colors.accent;
      var pulse = 0.5 + Math.sin(now * 0.004 + seed) * 0.5;
      ctx.strokeStyle = rgba(col, (0.04 + energy * 0.14) * pulse);
      ctx.lineWidth = 0.6;
      ctx.beginPath();
      ctx.arc(x, y, 8 + pulse * 22 * energy, 0, Math.PI * 2);
      ctx.stroke();
    }
    ctx.restore();
  }

  function drawBrane(ctx, w, h, now, color, energy) {
    var strands = 8 + Math.floor(energy * 12);
    var i;
    ctx.save();
    ctx.globalCompositeOperation = "screen";
    for (i = 0; i < strands; i += 1) {
      var yBase = h * (0.12 + (i / strands) * 0.76);
      ctx.strokeStyle = rgba(color, 0.04 + energy * 0.1);
      ctx.lineWidth = 1.2;
      ctx.beginPath();
      for (var x = 0; x <= w; x += 12) {
        var y = yBase + Math.sin(now * 0.001 + x * 0.008 + i * 0.9) * (18 + energy * 40);
        if (x === 0) ctx.moveTo(x, y); else ctx.lineTo(x, y);
      }
      ctx.stroke();
    }
    ctx.restore();
  }

  window.HyperspaceEngine = {
    updateEnergy: function () {
      return 0;
    },

    drawFull: function (ctx, w, h, now, th) {
      var e = this.updateEnergy();
      var cx = w * 0.5, cy = h * 0.42;
      var scale = Math.min(w, h) * (0.22 + e * 0.08);

      drawBrane(ctx, w, h, now, th.secondary, e);
      drawQuantumFoam(ctx, w, h, now, th, e);

      drawCalabiYau(ctx, cx, cy, scale * 1.1, now, th.accent, e);
      drawHyperObject(ctx, cube5, cx, cy, scale * 0.95, now, th.primary, e, true);
      drawHyperObject(ctx, cube6, cx * 0.28, cy * 1.15, scale * 0.55, now, th.secondary, e * 0.85, true);
      drawHyperObject(ctx, sphere10, cx * 1.38, cy * 0.88, scale * 0.72, now, th.glow || th.accent, e, false);
      drawHyperObject(ctx, cube5, cx * 0.72, cy * 0.28, scale * 0.42, now, th.accent, e * 0.7, true);
      drawHyperObject(ctx, sphere10, cx * 0.18, cy * 0.62, scale * 0.38, now, th.primary, e * 0.6, false);
    },

    drawCompact: function (ctx, w, h, now, th, boost) {
      var e = clamp(this.updateEnergy() + boost, 0, 1.5);
      var cx = w * 0.5, cy = h * 0.5;
      var scale = Math.min(w, h) * 0.38;
      drawCalabiYau(ctx, cx, cy, scale * 0.85, now, th.accent, e);
      drawHyperObject(ctx, cube5, cx, cy, scale * 0.72, now, th.primary, e, true);
      drawHyperObject(ctx, sphere10, cx, cy, scale * 0.55, now, th.secondary, e, false);
    },
  };
})();
