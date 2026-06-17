(function () {
  "use strict";

  var ORBIT_MAX = 368;

  var PLANETS = [
    { label: "Kotlin", orbit: 88, speed: 0.00055, radius: 14, color: "#7F52FF", logo: "assets/hero_kotlin.png", ring: false, moons: 1, gas: false, dance: 0.0 },
    { label: "Android", orbit: 128, speed: 0.00044, radius: 18, color: "#3DDC84", logo: "assets/hero_android.png", ring: false, moons: 2, gas: false, dance: 1.3 },
    { label: "CMP", orbit: 172, speed: 0.00036, radius: 15, color: "#42A5F5", logo: null, ring: true, moons: 0, gas: true, dance: 2.6 },
    { label: "AI", orbit: 218, speed: 0.00028, radius: 16, color: "#C084FC", logo: "assets/hero_ai.png", ring: false, moons: 1, gas: false, dance: 3.9 },
    { label: "iOS", orbit: 262, speed: 0.00022, radius: 13, color: "#0A84FF", logo: null, ring: false, moons: 0, gas: false, dance: 5.1 },
    { label: "KMP", orbit: 304, speed: 0.00018, radius: 12, color: "#F97316", logo: null, ring: false, moons: 0, gas: false, dance: 0.8 },
    { label: "TV", orbit: 348, speed: 0.00014, radius: 11, color: "#6366F1", logo: null, ring: false, moons: 0, gas: false, dance: 4.4 },
  ];

  function clamp(v, a, b) { return Math.max(a, Math.min(b, v)); }

  function cssVar(name) {
    return getComputedStyle(document.documentElement).getPropertyValue(name).trim();
  }

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

  function theme() {
    return {
      primary: cssVar("--primary") || "#3b82f6",
      secondary: cssVar("--secondary") || "#8b5cf6",
      accent: cssVar("--accent") || "#34d399",
      glow: cssVar("--accent-glow") || "#34d399",
    };
  }

  function initPlanets() {
    return PLANETS.map(function (p, i) {
      return {
        label: p.label, orbit: p.orbit, speed: p.speed, radius: p.radius,
        color: p.color, logo: p.logo, ring: p.ring, moons: p.moons, gas: p.gas, dance: p.dance,
        angle: (i / PLANETS.length) * Math.PI * 2 + Math.random(),
        tilt: 0.34 + Math.random() * 0.14,
        spin: Math.random() * Math.PI * 2,
        wobble: Math.random() * Math.PI * 2,
      };
    });
  }

  function preloadLogos(planets) {
    var cache = {};
    planets.forEach(function (p) {
      if (!p.logo || cache[p.logo]) return;
      var img = new Image();
      img.src = p.logo;
      cache[p.logo] = img;
    });
    return cache;
  }

  function initHeroSolar() {
    var canvas = document.getElementById("hero-solar");
    if (!canvas || canvas.__heroSolarRunning) return;
    if (window.matchMedia && window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;
    var core = canvas.closest(".avatar-core");
    if (!core) return;

    var ctx = canvas.getContext("2d");
    if (!ctx) return;

    canvas.__heroSolarRunning = true;
    var planets = initPlanets();
    var logos = preloadLogos(planets);
    var sparks = [];
    var width = 0, height = 0, dpr = 1, scale = 1;
    var pointer = { x: 0.5, y: 0.5 };
    var smoothPtr = { x: 0.5, y: 0.5 };

    function resize() {
      var r = core.getBoundingClientRect();
      width = Math.max(1, r.width);
      height = Math.max(1, r.height);
      dpr = Math.min(2.5, Math.max(1, window.devicePixelRatio || 1));
      canvas.width = Math.floor(width * dpr);
      canvas.height = Math.floor(height * dpr);
      canvas.style.width = width + "px";
      canvas.style.height = height + "px";
      ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
      scale = clamp(Math.min(width, height) / ORBIT_MAX, 0.72, 1.35);
    }

    function center() {
      var parX = (smoothPtr.x - 0.5) * width * 0.06;
      var parY = (smoothPtr.y - 0.5) * height * 0.06;
      return { x: width * 0.5 + parX, y: height * 0.5 + parY };
    }

    function orbitPos(cx, cy, orbit, angle, tilt, danceAmp, now, dancePhase) {
      var pulse = 1 + Math.sin(now * 0.0022 + dancePhase) * danceAmp;
      var bob = Math.sin(now * 0.0038 + dancePhase * 1.7) * orbit * 0.04;
      var o = orbit * pulse + bob;
      var z = Math.sin(angle + Math.sin(now * 0.0015 + dancePhase) * 0.35);
      return {
        x: cx + Math.cos(angle) * o,
        y: cy + Math.sin(angle) * o * tilt,
        z: z,
        scale: 0.58 + (z + 1) * 0.32,
        alpha: 0.5 + (z + 1) * 0.32,
      };
    }

    function drawTesseract(cx, cy, r, now, th) {
      var dims = 16;
      var pts = [];
      for (var i = 0; i < dims; i += 1) {
        var a = (i / dims) * Math.PI * 2;
        var w = now * 0.00035 + i * 0.4;
        var rad = r * (0.55 + Math.sin(w) * 0.12);
        pts.push({
          x: cx + Math.cos(a + w * 0.6) * rad,
          y: cy + Math.sin(a * 1.3 + w * 0.5) * rad * 0.72,
          z: Math.sin(w * 1.8),
        });
      }
      ctx.save();
      ctx.globalCompositeOperation = "screen";
      for (var e = 0; e < dims; e += 1) {
        for (var f = e + 1; f < dims; f += 1) {
          if ((e + f) % 5 !== 0 && (e + f) % 7 !== 0) continue;
          var p1 = pts[e], p2 = pts[f];
          var depth = (p1.z + p2.z) * 0.5;
          ctx.strokeStyle = rgba(th.secondary, 0.06 + depth * 0.08);
          ctx.lineWidth = 0.8;
          ctx.beginPath();
          ctx.moveTo(p1.x, p1.y);
          ctx.lineTo(p2.x, p2.y);
          ctx.stroke();
        }
      }
      ctx.restore();
    }

    function drawHyperRings(cx, cy, now, th) {
      var rings = 12;
      for (var i = 0; i < rings; i += 1) {
        var orbit = (58 + i * 38) * scale;
        var tilt = 0.18 + i * 0.09;
        var spin = now * (0.00028 + i * 0.00007) + i * 1.15;
        var wobble = 1 + Math.sin(now * 0.002 + i * 0.9) * 0.09;
        var tint = i % 3 === 0 ? th.primary : i % 3 === 1 ? th.secondary : th.accent;
        ctx.save();
        ctx.translate(cx, cy);
        ctx.rotate(spin);
        ctx.scale(wobble, tilt);
        ctx.strokeStyle = rgba(tint, 0.16 + i * 0.02);
        ctx.lineWidth = 1.1 + i * 0.1;
        ctx.setLineDash([4 + i, 8 + i * 2]);
        ctx.beginPath();
        ctx.ellipse(0, 0, orbit, orbit * 0.9, i * 0.28, 0, Math.PI * 2);
        ctx.stroke();
        ctx.setLineDash([]);
        ctx.restore();
      }
    }

    function drawSun(cx, cy, now, th) {
      var r = 38 * scale;
      var pulse = 1 + Math.sin(now * 0.0024) * 0.1;
      var dance = Math.sin(now * 0.0032) * 3;

      for (var i = 7; i >= 0; i -= 1) {
        var gr = r * (2.2 + i * 0.55) * pulse;
        var corona = ctx.createRadialGradient(cx, cy + dance, r * 0.1, cx, cy, gr);
        corona.addColorStop(0, rgba(th.glow, 0.28 - i * 0.028));
        corona.addColorStop(0.5, rgba(th.primary, 0.12 - i * 0.01));
        corona.addColorStop(1, "rgba(0,0,0,0)");
        ctx.fillStyle = corona;
        ctx.beginPath();
        ctx.arc(cx, cy + dance, gr, 0, Math.PI * 2);
        ctx.fill();
      }

      var rays = 20;
      for (var ri = 0; ri < rays; ri += 1) {
        var ang = (ri / rays) * Math.PI * 2 + now * 0.00055;
        var len = r * (2.4 + Math.sin(now * 0.003 + ri * 0.8) * 0.8);
        ctx.strokeStyle = rgba(th.accent, 0.07 + Math.sin(now * 0.004 + ri) * 0.05);
        ctx.lineWidth = 1.2 + Math.sin(now * 0.005 + ri) * 0.8;
        ctx.beginPath();
        ctx.moveTo(cx + Math.cos(ang) * r, cy + dance + Math.sin(ang) * r);
        ctx.lineTo(cx + Math.cos(ang) * len, cy + dance + Math.sin(ang) * len);
        ctx.stroke();
      }

      var body = ctx.createRadialGradient(cx - r * 0.3, cy - r * 0.3 + dance, 1, cx, cy + dance, r * pulse);
      body.addColorStop(0, "rgba(255,255,255,0.95)");
      body.addColorStop(0.35, rgba(th.glow, 0.9));
      body.addColorStop(0.7, rgba(th.primary, 0.75));
      body.addColorStop(1, rgba(th.secondary, 0.35));
      ctx.fillStyle = body;
      ctx.beginPath();
      ctx.arc(cx, cy + dance, r * pulse, 0, Math.PI * 2);
      ctx.fill();
    }

    function drawPlanet(x, y, planet, pos, now, th, sunX, sunY) {
      var r = planet.radius * pos.scale * scale;
      var wobble = Math.sin(now * 0.004 + planet.wobble) * 0.15;
      planet.spin += 0.012;

      var rgb = hexToRgb(planet.color);
      var atmo = ctx.createRadialGradient(x, y, r * 0.8, x, y, r * 1.5);
      atmo.addColorStop(0, rgba(rgb, 0.08 * pos.alpha));
      atmo.addColorStop(1, "rgba(0,0,0,0)");
      ctx.fillStyle = atmo;
      ctx.beginPath();
      ctx.arc(x, y, r * 1.5, 0, Math.PI * 2);
      ctx.fill();

      var shade = ctx.createRadialGradient(x - r * 0.35, y - r * 0.35, r * 0.1, x, y, r);
      shade.addColorStop(0, "rgba(255,255,255,0.65)");
      shade.addColorStop(0.45, rgba(rgb, 0.95));
      shade.addColorStop(1, rgba(rgb, 0.25));
      ctx.fillStyle = shade;
      ctx.beginPath();
      ctx.arc(x, y, r, 0, Math.PI * 2);
      ctx.fill();

      ctx.save();
      ctx.translate(x, y);
      ctx.rotate(planet.spin + wobble);
      ctx.beginPath();
      ctx.arc(0, 0, r * 0.96, 0, Math.PI * 2);
      ctx.clip();
      if (planet.logo && logos[planet.logo] && logos[planet.logo].complete) {
        var sz = r * 1.55;
        ctx.drawImage(logos[planet.logo], -sz / 2, -sz / 2, sz, sz);
      } else if (planet.label) {
        ctx.fillStyle = "rgba(255,255,255,0.92)";
        ctx.font = "bold " + Math.max(8, Math.round(r * 0.7)) + "px system-ui,sans-serif";
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(planet.label, 0, 0);
      }
      ctx.restore();

      if (planet.ring) {
        ctx.save();
        ctx.translate(x, y);
        ctx.scale(1, 0.28);
        ctx.strokeStyle = rgba(rgb, 0.55 * pos.alpha);
        ctx.lineWidth = 3;
        ctx.beginPath();
        ctx.arc(0, 0, r * 1.65, 0, Math.PI * 2);
        ctx.stroke();
        ctx.restore();
      }

      for (var m = 0; m < planet.moons; m += 1) {
        var ma = now * 0.004 + m * 2.2 + planet.angle;
        var mr = r * (1.85 + m * 0.7);
        var mx = x + Math.cos(ma) * mr;
        var my = y + Math.sin(ma) * mr * 0.5;
        ctx.fillStyle = "rgba(210,230,255,0.8)";
        ctx.beginPath();
        ctx.arc(mx, my, 2 + m, 0, Math.PI * 2);
        ctx.fill();
      }
    }

    function spawnSpark(cx, cy) {
      if (sparks.length > 40) return;
      var ang = Math.random() * Math.PI * 2;
      var dist = (40 + Math.random() * 120) * scale;
      sparks.push({
        x: cx + Math.cos(ang) * dist,
        y: cy + Math.sin(ang) * dist,
        vx: (Math.random() - 0.5) * 0.6,
        vy: (Math.random() - 0.5) * 0.6,
        life: 1,
        hue: Math.random() > 0.5 ? theme().primary : theme().accent,
      });
    }

    function drawSparks(now, th, musicE) {
      for (var i = sparks.length - 1; i >= 0; i -= 1) {
        var s = sparks[i];
        s.x += s.vx;
        s.y += s.vy;
        s.life -= 0.018;
        if (s.life <= 0) { sparks.splice(i, 1); continue; }
        ctx.fillStyle = rgba(s.hue, s.life * 0.7);
        ctx.beginPath();
        ctx.arc(s.x, s.y, 1.5 * s.life, 0, Math.PI * 2);
        ctx.fill();
      }
      if (Math.random() < 0.12 + musicE * 0.4) spawnSpark(width * 0.5, height * 0.5);
    }

    function frame(now) {
      smoothPtr.x += (pointer.x - smoothPtr.x) * 0.06;
      smoothPtr.y += (pointer.y - smoothPtr.y) * 0.06;

      var th = theme();
      var c = center();
      var musicE = window.HyperspaceEngine ? window.HyperspaceEngine.updateEnergy() : 0;
      var speedMul = 1 + musicE * 2.2;

      ctx.clearRect(0, 0, width, height);

      var bg = ctx.createRadialGradient(c.x, c.y, 0, c.x, c.y, Math.max(width, height) * 0.55);
      bg.addColorStop(0, "rgba(8,16,40,0.12)");
      bg.addColorStop(0.55, "rgba(4,8,22,0.5)");
      bg.addColorStop(1, "rgba(0,0,0,0.88)");
      ctx.fillStyle = bg;
      ctx.fillRect(0, 0, width, height);

      if (window.HyperspaceEngine) window.HyperspaceEngine.drawCompact(ctx, width, height, now, th, 0.35);
      drawTesseract(c.x, c.y, Math.min(width, height) * 0.44, now, th);
      drawHyperRings(c.x, c.y, now, th);

      planets.forEach(function (p) {
        p.angle += p.speed * 22 * speedMul;
      });

      var positions = planets.map(function (p) {
        var danceAmp = (0.06 + Math.sin(p.dance) * 0.02) * (1 + musicE * 0.85);
        return {
          planet: p,
          pos: orbitPos(c.x, c.y, p.orbit * scale, p.angle, p.tilt, danceAmp, now, p.dance),
        };
      });
      positions.sort(function (a, b) { return a.pos.z - b.pos.z; });

      positions.forEach(function (item) {
        var p = item.planet;
        var pos = item.pos;
        ctx.save();
        ctx.strokeStyle = rgba(p.color, 0.14 * pos.alpha);
        ctx.lineWidth = 1.2;
        ctx.beginPath();
        for (var t = 0; t <= 20; t += 1) {
          var ang = p.angle - (t / 20) * Math.PI * 1.4;
          var o = p.orbit * scale * (1 + Math.sin(now * 0.0022 + p.dance) * 0.06);
          var px = c.x + Math.cos(ang) * o;
          var py = c.y + Math.sin(ang) * o * p.tilt;
          if (t === 0) ctx.moveTo(px, py); else ctx.lineTo(px, py);
        }
        ctx.stroke();
        ctx.restore();
      });

      drawSun(c.x, c.y, now, th);

      positions.forEach(function (item) {
        drawPlanet(item.pos.x, item.pos.y, item.planet, item.pos, now, th, c.x, c.y);
        if (item.pos.z > 0.1) {
          ctx.fillStyle = rgba(item.planet.color, 0.75 * item.pos.alpha);
          ctx.font = "600 " + Math.round(8 * scale) + "px ui-monospace,monospace";
          ctx.textAlign = "center";
          ctx.fillText(item.planet.label, item.pos.x, item.pos.y + item.planet.radius * item.pos.scale * scale + 12);
        }
      });

      drawSparks(now, th, musicE);
      requestAnimationFrame(frame);
    }

    function onPointer(e) {
      var r = core.getBoundingClientRect();
      pointer.x = clamp((e.clientX - r.left) / r.width, 0, 1);
      pointer.y = clamp((e.clientY - r.top) / r.height, 0, 1);
    }

    core.addEventListener("mousemove", onPointer, { passive: true });
    window.addEventListener("resize", resize, { passive: true });
    window.addEventListener("scroll", resize, { passive: true });

    resize();
    requestAnimationFrame(frame);
  }

  window.portfolioHeroSolarInit = initHeroSolar;

  function boot() {
    initHeroSolar();
    if (!document.getElementById("hero-solar")) {
      window.setTimeout(initHeroSolar, 80);
      window.setTimeout(initHeroSolar, 400);
      window.setTimeout(initHeroSolar, 1200);
    }
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", boot, { once: true });
  } else {
    boot();
  }
})();
