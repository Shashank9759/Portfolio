(function () {
  "use strict";

  var DESKTOP_MIN = 1024;
  var TABLET_MIN = 600;

  var TECH_PLANETS = [
    { label: "Kotlin", orbit: 88, speed: 0.00042, radius: 13, color: "#7F52FF", logo: "assets/hero_kotlin.png", ring: false, moons: 1, gas: false },
    { label: "Android", orbit: 128, speed: 0.00034, radius: 17, color: "#3DDC84", logo: "assets/hero_android.png", ring: false, moons: 2, gas: false },
    { label: "CMP", orbit: 172, speed: 0.00027, radius: 14, color: "#42A5F5", logo: null, ring: true, moons: 0, gas: true },
    { label: "AI / ML", orbit: 218, speed: 0.00021, radius: 15, color: "#C084FC", logo: "assets/hero_ai.png", ring: false, moons: 1, gas: false },
    { label: "iOS", orbit: 262, speed: 0.00017, radius: 12, color: "#0A84FF", logo: null, ring: false, moons: 0, gas: false },
    { label: "KMP", orbit: 304, speed: 0.00013, radius: 11, color: "#F97316", logo: null, ring: false, moons: 0, gas: false },
    { label: "TV", orbit: 348, speed: 0.0001, radius: 10, color: "#6366F1", logo: null, ring: false, moons: 0, gas: false },
  ];

  var MINI_SYSTEMS = [
    { x: 0.12, y: 0.78, scale: 0.32, hue: 0, planets: 4 },
    { x: 0.9, y: 0.88, scale: 0.26, hue: 2.1, planets: 3 },
    { x: 0.08, y: 0.22, scale: 0.22, hue: 4.2, planets: 3 },
  ];

  function clamp(v, a, b) { return Math.max(a, Math.min(b, v)); }
  function rand(a, b) { return a + Math.random() * (b - a); }

  function cssVar(name) {
    return getComputedStyle(document.documentElement).getPropertyValue(name).trim();
  }

  function hexToRgb(hex) {
    if (!hex || hex[0] !== "#") return { r: 255, g: 255, b: 255 };
    var c = hex.slice(1);
    if (c.length === 3) c = c[0] + c[0] + c[1] + c[1] + c[2] + c[2];
    var n = parseInt(c, 16);
    return { r: (n >> 16) & 255, g: (n >> 8) & 255, b: n & 255 };
  }

  function parseColor(value, fbHex, fbA) {
    var fb = hexToRgb(fbHex);
    var out = { r: fb.r, g: fb.g, b: fb.b, a: fbA };
    if (!value) return out;
    var m = value.match(/^rgba?\(([^)]+)\)$/i);
    if (m) {
      var p = m[1].split(",").map(function (x) { return parseFloat(x.trim()); });
      if (p.length >= 3) {
        return { r: clamp(p[0], 0, 255), g: clamp(p[1], 0, 255), b: clamp(p[2], 0, 255), a: p[3] !== undefined ? clamp(p[3], 0, 1) : fbA };
      }
    }
    if (value[0] === "#") {
      var rgb = hexToRgb(value);
      return { r: rgb.r, g: rgb.g, b: rgb.b, a: fbA };
    }
    return out;
  }

  function rgba(c, a) {
    return "rgba(" + c.r + "," + c.g + "," + c.b + "," + (a !== undefined ? a : c.a) + ")";
  }

  function getCanvas() {
    return document.getElementById("dev-bg") || document.getElementById("dev-bg-canvas") || document.querySelector(".dev-bg-canvas");
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

  function createStars(n, spread) {
    var arr = [];
    for (var i = 0; i < n; i += 1) {
      arr.push({ x: (Math.random() - 0.5) * spread, y: (Math.random() - 0.5) * spread, z: Math.random(), tw: Math.random() * Math.PI * 2 });
    }
    return arr;
  }

  function createGalaxyDust(n) {
    var arr = [];
    for (var i = 0; i < n; i += 1) {
      arr.push({ arm: Math.floor(Math.random() * 3), t: Math.random(), jitter: (Math.random() - 0.5) * 0.18, size: 0.4 + Math.random() * 1.8, alpha: 0.15 + Math.random() * 0.55 });
    }
    return arr;
  }

  function createAsteroids(n, oMin, oMax) {
    var arr = [];
    for (var i = 0; i < n; i += 1) {
      arr.push({ orbit: rand(oMin, oMax), angle: Math.random() * Math.PI * 2, speed: rand(0.00008, 0.0002), size: rand(0.8, 2.4), wobble: Math.random() * Math.PI * 2 });
    }
    return arr;
  }

  function createNebulae(n, w, h) {
    var arr = [];
    for (var i = 0; i < n; i += 1) {
      arr.push({ x: Math.random() * w, y: Math.random() * h, r: rand(80, 260), phase: Math.random() * Math.PI * 2, drift: rand(0.0001, 0.0004), spin: rand(-0.0003, 0.0003), tint: Math.floor(Math.random() * 3) });
    }
    return arr;
  }

  function createWormholes(n, w, h) {
    var arr = [];
    for (var i = 0; i < n; i += 1) {
      arr.push({ x: rand(w * 0.1, w * 0.9), y: rand(h * 0.15, h * 0.85), r: rand(35, 90), spin: rand(0.0004, 0.0012) * (Math.random() > 0.5 ? 1 : -1), phase: Math.random() * Math.PI * 2, tilt: rand(0.35, 0.75) });
    }
    return arr;
  }

  function createComets(max) {
    var pool = [];
    for (var i = 0; i < max; i += 1) pool.push({ active: false });
    return pool;
  }

  function createSolarFlares(max) {
    var pool = [];
    for (var i = 0; i < max; i += 1) pool.push({ active: false });
    return pool;
  }

  function createAliens(n, w, h) {
    var types = ["ufo", "floater", "jelly", "scout"];
    var arr = [];
    for (var i = 0; i < n; i += 1) {
      var type = types[Math.floor(Math.random() * types.length)];
      arr.push({
        type: type,
        x: Math.random() * w,
        y: Math.random() * h,
        baseX: Math.random() * w,
        baseY: Math.random() * h,
        scale: type === "ufo" ? rand(0.7, 1.3) : rand(0.45, 1),
        phase: Math.random() * Math.PI * 2,
        speed: rand(0.0002, 0.0008),
        driftR: rand(25, 90),
        dir: Math.random() > 0.5 ? 1 : -1,
        pathY: rand(h * 0.08, h * 0.92),
        vx: rand(0.4, 1.2) * (Math.random() > 0.5 ? 1 : -1),
        blink: Math.random() * Math.PI * 2,
        color: Math.random() > 0.5 ? "#4ade80" : "#a78bfa",
      });
    }
    return arr;
  }

  function spawnComet(pool, w, h) {
    for (var i = 0; i < pool.length; i += 1) {
      if (!pool[i].active) {
        var fromLeft = Math.random() > 0.5;
        pool[i] = { active: true, x: fromLeft ? -40 : w + 40, y: rand(h * 0.05, h * 0.55), vx: fromLeft ? rand(4, 9) : rand(-9, -4), vy: rand(1.5, 4.5), tail: rand(90, 180), life: 1, decay: rand(0.004, 0.008) };
        return;
      }
    }
  }

  function spawnSolarFlare(pool, cx, cy, r) {
    for (var i = 0; i < pool.length; i += 1) {
      if (!pool[i].active) {
        var ang = Math.random() * Math.PI * 2;
        pool[i] = { active: true, angle: ang, dist: r * 1.1, len: rand(r * 0.8, r * 2.2), life: 1, decay: rand(0.008, 0.018), width: rand(2, 5) };
        return;
      }
    }
  }

  function initPlanets(template, scale) {
    return template.map(function (p, i) {
      return {
        label: p.label, orbit: p.orbit * scale, speed: p.speed, radius: p.radius * scale,
        color: p.color, logo: p.logo, ring: p.ring, moons: p.moons, gas: p.gas,
        angle: (i / template.length) * Math.PI * 2 + Math.random() * 0.5,
        tilt: 0.38 + Math.random() * 0.12, spin: Math.random() * Math.PI * 2,
      };
    });
  }

  function init() {
    if (!document.body) return;
    if (window.matchMedia && window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;

    var canvas = getCanvas();
    if (!canvas) return;
    var ctx = canvas.getContext("2d");
    if (!ctx) return;

    var mode = "mobile";
    var width = 0, height = 0, dpr = 1;
    var stars = [], galaxyDust = [], asteroids = [], nebulae = [], wormholes = [];
    var comets = createComets(5), flares = createSolarFlares(8);
    var aliens = [], planets = [];
    var logoCache = preloadLogos(TECH_PLANETS);
    var scrollY = 0, pointer = { x: 0, y: 0 }, smooth = { x: 0, y: 0, vx: 0, vy: 0 };
    var lastComet = 0, lastFlare = 0, spread = 1800;

    function theme() {
      return {
        bg: parseColor(cssVar("--background"), "#030308", 1),
        primary: parseColor(cssVar("--primary"), "#3b82f6", 1),
        secondary: parseColor(cssVar("--secondary"), "#8b5cf6", 1),
        accent: parseColor(cssVar("--accent"), "#34d399", 1),
        glow: parseColor(cssVar("--accent-glow"), "#34d399", 0.5),
        particle: parseColor(cssVar("--particle"), "#60a5fa", 0.8),
      };
    }

    function profile() {
      if (mode === "desktop") return { stars: 520, galaxy: 900, asteroids: 120, nebulae: 7, wormholes: 4, aliens: 14, sysX: 0.74, sysY: 0.4, sysScale: 1, warp: 0.024, miniSystems: 3 };
      if (mode === "tablet") return { stars: 280, galaxy: 450, asteroids: 60, nebulae: 4, wormholes: 2, aliens: 8, sysX: 0.68, sysY: 0.35, sysScale: 0.78, warp: 0.018, miniSystems: 2 };
      return { stars: 110, galaxy: 180, asteroids: 25, nebulae: 2, wormholes: 1, aliens: 4, sysX: 0.5, sysY: 0.28, sysScale: 0.52, warp: 0.012, miniSystems: 1 };
    }

    function solarCenter(p) {
      var parX = (smooth.x / Math.max(1, width) - 0.5) * 28;
      var parY = (smooth.y / Math.max(1, height) - 0.5) * 22 + scrollY * 0.12;
      return { x: width * p.sysX + parX, y: height * p.sysY + parY };
    }

    function resize() {
      var w = window.innerWidth || document.documentElement.clientWidth || 0;
      var h = window.innerHeight || document.documentElement.clientHeight || 0;
      var nextMode = w >= DESKTOP_MIN ? "desktop" : w >= TABLET_MIN ? "tablet" : "mobile";
      var changed = nextMode !== mode || w !== width || h !== height;
      mode = nextMode; width = w; height = h;
      dpr = Math.min(2, Math.max(1, window.devicePixelRatio || 1));
      canvas.width = Math.floor(width * dpr);
      canvas.height = Math.floor(height * dpr);
      ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
      if (changed) {
        var pf = profile();
        stars = createStars(pf.stars, spread);
        galaxyDust = createGalaxyDust(pf.galaxy);
        asteroids = createAsteroids(pf.asteroids, 158 * pf.sysScale, 182 * pf.sysScale);
        nebulae = createNebulae(pf.nebulae, width, height);
        wormholes = createWormholes(pf.wormholes, width, height);
        aliens = createAliens(pf.aliens, width, height);
        planets = initPlanets(TECH_PLANETS, pf.sysScale);
      }
    }

    function updatePointer() {
      var tx = pointer.x - smooth.x, ty = pointer.y - smooth.y;
      smooth.vx = (smooth.vx + tx * 0.05) * 0.86;
      smooth.vy = (smooth.vy + ty * 0.05) * 0.86;
      smooth.x += smooth.vx; smooth.y += smooth.vy;
    }

    function orbitPos(cx, cy, orbit, angle, tilt) {
      var z = Math.sin(angle);
      return { x: cx + Math.cos(angle) * orbit, y: cy + Math.sin(angle) * orbit * tilt, z: z, scale: 0.55 + (z + 1) * 0.28, alpha: 0.45 + (z + 1) * 0.28 };
    }

    function drawCosmos() {
      var th = theme();
      ctx.fillStyle = rgba(th.bg, 1);
      ctx.fillRect(0, 0, width, height);
      var cx = width * 0.5, cy = height * 0.45;
      var g = ctx.createRadialGradient(cx, cy * 0.3, 0, cx, cy, Math.max(width, height));
      g.addColorStop(0, rgba(th.secondary, 0.22));
      g.addColorStop(0.25, rgba(th.primary, 0.12));
      g.addColorStop(0.55, rgba(th.bg, 0.04));
      g.addColorStop(1, "rgba(0,0,0,0.94)");
      ctx.fillStyle = g;
      ctx.fillRect(0, 0, width, height);
    }

    function drawGalaxy(now) {
      var th = theme(), pf = profile(), center = solarCenter(pf);
      var rot = now * 0.00004, maxR = Math.min(width, height) * (mode === "desktop" ? 0.55 : 0.42);
      var parX = (smooth.x / Math.max(1, width) - 0.5) * 18, parY = (smooth.y / Math.max(1, height) - 0.5) * 14;
      ctx.save(); ctx.globalCompositeOperation = "screen";
      galaxyDust.forEach(function (d) {
        var angle = d.t * Math.PI * 5 + d.arm * (Math.PI * 2 / 3) + rot + d.jitter;
        var r = d.t * maxR;
        var tint = d.arm === 0 ? th.primary : d.arm === 1 ? th.secondary : th.accent;
        ctx.fillStyle = rgba(tint, d.alpha * 0.35);
        ctx.beginPath();
        ctx.arc(center.x + parX + Math.cos(angle) * r, center.y + parY + Math.sin(angle) * r * 0.42, d.size, 0, Math.PI * 2);
        ctx.fill();
      });
      ctx.restore();
    }

    function drawNebulae(now) {
      var th = theme();
      var parX = (smooth.x / Math.max(1, width) - 0.5) * 50;
      var parY = (smooth.y / Math.max(1, height) - 0.5) * 40 + scrollY * 0.2;
      nebulae.forEach(function (n, i) {
        var pulse = 0.6 + Math.sin(now * n.drift * 1200 + n.phase) * 0.4;
        var nx = n.x + Math.sin(now * n.spin + n.phase) * 45 + parX * (0.2 + i * 0.1);
        var ny = n.y + Math.cos(now * n.spin * 0.7 + n.phase) * 35 + parY * (0.2 + i * 0.1);
        var tint = n.tint === 0 ? th.secondary : n.tint === 1 ? th.primary : th.accent;
        var grad = ctx.createRadialGradient(nx, ny, 0, nx, ny, n.r * pulse);
        grad.addColorStop(0, rgba(tint, 0.28 * pulse));
        grad.addColorStop(0.4, rgba(tint, 0.1 * pulse));
        grad.addColorStop(1, "rgba(0,0,0,0)");
        ctx.fillStyle = grad;
        ctx.beginPath(); ctx.arc(nx, ny, n.r * pulse, 0, Math.PI * 2); ctx.fill();
      });
    }

    function drawWormholes(now) {
      var th = theme();
      wormholes.forEach(function (w) {
        var spin = now * w.spin + w.phase, layers = mode === "mobile" ? 4 : 7;
        for (var l = 0; l < layers; l += 1) {
          var t = l / layers, r = w.r * (1 - t * 0.75);
          ctx.save(); ctx.translate(w.x, w.y); ctx.rotate(spin + t * 1.8); ctx.scale(1, w.tilt);
          ctx.strokeStyle = rgba(l % 2 === 0 ? th.primary : th.accent, 0.08 + t * 0.14);
          ctx.lineWidth = 1.2 + t * 2;
          ctx.beginPath(); ctx.arc(0, 0, r, 0, Math.PI * 2); ctx.stroke();
          ctx.restore();
        }
        var core = ctx.createRadialGradient(w.x, w.y, 0, w.x, w.y, w.r * 0.35);
        core.addColorStop(0, rgba(th.glow, 0.22)); core.addColorStop(1, "rgba(0,0,0,0)");
        ctx.fillStyle = core;
        ctx.beginPath(); ctx.arc(w.x, w.y, w.r * 0.35, 0, Math.PI * 2); ctx.fill();
      });
    }

    function drawWarpStars(now) {
      var th = theme(), pf = profile(), center = solarCenter(pf), speed = pf.warp;
      stars.forEach(function (s) {
        s.z -= speed * (0.5 + s.z * 0.9);
        if (s.z <= 0.015) { s.x = (Math.random() - 0.5) * spread; s.y = (Math.random() - 0.5) * spread; s.z = 1; }
        var depth = 1 - s.z, fov = Math.min(width, height) * 0.58;
        var sx = center.x + s.x * (1 / s.z) * fov * 0.0011;
        var sy = center.y + s.y * (1 / s.z) * fov * 0.0011;
        if (sx < -30 || sx > width + 30 || sy < -30 || sy > height + 30) return;
        var size = 0.35 + depth * 3.4, tw = 0.6 + Math.sin(now * 0.005 + s.tw) * 0.4, a = clamp(depth * tw, 0.06, 1);
        if (depth > 0.72 && mode !== "mobile") {
          var ang = Math.atan2(sy - center.y, sx - center.x), streak = size * (6 + depth * 22);
          var lg = ctx.createLinearGradient(sx - Math.cos(ang) * streak, sy - Math.sin(ang) * streak, sx, sy);
          lg.addColorStop(0, "rgba(255,255,255,0)");
          lg.addColorStop(0.35, rgba(th.particle, a * 0.4));
          lg.addColorStop(1, rgba(th.glow, a));
          ctx.strokeStyle = lg; ctx.lineWidth = size * 0.65;
          ctx.beginPath(); ctx.moveTo(sx - Math.cos(ang) * streak, sy - Math.sin(ang) * streak); ctx.lineTo(sx, sy); ctx.stroke();
        } else {
          ctx.fillStyle = rgba(depth > 0.45 ? th.glow : th.particle, a * 0.9);
          ctx.beginPath(); ctx.arc(sx, sy, size, 0, Math.PI * 2); ctx.fill();
        }
      });
    }

    function drawSunFlares(cx, cy, r, alpha, now) {
      var th = theme();
      if (now - lastFlare > 600 + Math.random() * 900) { spawnSolarFlare(flares, cx, cy, r); lastFlare = now; }
      flares.forEach(function (f) {
        if (!f.active) return;
        f.life -= f.decay;
        if (f.life <= 0) { f.active = false; return; }
        var fx = cx + Math.cos(f.angle) * f.dist, fy = cy + Math.sin(f.angle) * f.dist;
        var ex = fx + Math.cos(f.angle) * f.len * f.life, ey = fy + Math.sin(f.angle) * f.len * f.life;
        var grad = ctx.createLinearGradient(fx, fy, ex, ey);
        grad.addColorStop(0, rgba(th.glow, 0.7 * f.life * alpha));
        grad.addColorStop(0.5, rgba(th.accent, 0.4 * f.life * alpha));
        grad.addColorStop(1, "rgba(255,200,80,0)");
        ctx.strokeStyle = grad; ctx.lineWidth = f.width * f.life;
        ctx.beginPath(); ctx.moveTo(fx, fy); ctx.lineTo(ex, ey); ctx.stroke();
      });
    }

    function drawSun(cx, cy, scale, alpha, now) {
      var th = theme(), r = 38 * scale, pulse = 1 + Math.sin(now * 0.0018) * 0.08;

      for (var i = 6; i >= 0; i -= 1) {
        var gr = r * (2.4 + i * 0.6) * pulse;
        var corona = ctx.createRadialGradient(cx, cy, r * 0.15, cx, cy, gr);
        corona.addColorStop(0, rgba(th.glow, (0.22 - i * 0.025) * alpha));
        corona.addColorStop(0.45, rgba(th.primary, (0.1 - i * 0.012) * alpha));
        corona.addColorStop(1, "rgba(0,0,0,0)");
        ctx.fillStyle = corona; ctx.beginPath(); ctx.arc(cx, cy, gr, 0, Math.PI * 2); ctx.fill();
      }

      var rays = mode === "mobile" ? 10 : 18;
      for (var ri = 0; ri < rays; ri += 1) {
        var ang = (ri / rays) * Math.PI * 2 + now * 0.0004;
        var len = r * (2.6 + Math.sin(now * 0.0025 + ri * 0.7) * 0.7);
        ctx.strokeStyle = rgba(th.accent, (0.08 + Math.sin(now * 0.003 + ri) * 0.06) * alpha);
        ctx.lineWidth = 1.5 + Math.sin(now * 0.004 + ri) * 1;
        ctx.beginPath();
        ctx.moveTo(cx + Math.cos(ang) * r * 1.05, cy + Math.sin(ang) * r * 1.05);
        ctx.lineTo(cx + Math.cos(ang) * len, cy + Math.sin(ang) * len);
        ctx.stroke();
      }

      ctx.save(); ctx.translate(cx, cy); ctx.rotate(now * 0.00015);
      for (var b = 0; b < 5; b += 1) {
        ctx.strokeStyle = "rgba(255,180,60," + (0.12 * alpha) + ")";
        ctx.lineWidth = 3 + b;
        ctx.beginPath(); ctx.arc(0, 0, r * (0.55 + b * 0.08), b * 0.4, b * 0.4 + Math.PI * 0.9); ctx.stroke();
      }
      ctx.restore();

      var body = ctx.createRadialGradient(cx - r * 0.25, cy - r * 0.25, 1, cx, cy, r * pulse);
      body.addColorStop(0, "rgba(255,255,255,0.98)");
      body.addColorStop(0.2, "rgba(255,230,120,0.95)");
      body.addColorStop(0.5, rgba(th.glow, 0.9 * alpha));
      body.addColorStop(0.8, rgba(th.primary, 0.7 * alpha));
      body.addColorStop(1, rgba(th.secondary, 0.4 * alpha));
      ctx.fillStyle = body; ctx.beginPath(); ctx.arc(cx, cy, r * pulse, 0, Math.PI * 2); ctx.fill();

      drawSunFlares(cx, cy, r * pulse, alpha, now);

      ctx.fillStyle = "rgba(255,255,255," + (0.9 * alpha) + ")";
      ctx.font = "bold " + Math.round(10 * scale) + "px system-ui,sans-serif";
      ctx.textAlign = "center"; ctx.textBaseline = "middle";
      ctx.fillText("MOBILE", cx, cy - 5 * scale);
      ctx.font = Math.round(8 * scale) + "px system-ui,sans-serif";
      ctx.fillStyle = rgba(th.particle, 0.85 * alpha);
      ctx.fillText("UNIVERSE", cx, cy + 7 * scale);
    }

    function drawOrbitPath(cx, cy, orbit, tilt, alpha, color) {
      ctx.save(); ctx.translate(cx, cy); ctx.scale(1, tilt);
      ctx.strokeStyle = rgba(color, alpha); ctx.lineWidth = 1; ctx.setLineDash([3, 8]);
      ctx.beginPath(); ctx.arc(0, 0, orbit, 0, Math.PI * 2); ctx.stroke(); ctx.setLineDash([]); ctx.restore();
    }

    function drawPlanetBody(x, y, planet, pos, alpha, sunX, sunY) {
      var r = planet.radius * pos.scale, rgb = hexToRgb(planet.color);
      planet.spin += 0.008;

      var atmo = ctx.createRadialGradient(x, y, r * 0.85, x, y, r * 1.45);
      atmo.addColorStop(0, rgba(rgb, 0.05 * alpha));
      atmo.addColorStop(0.6, rgba(rgb, 0.18 * alpha));
      atmo.addColorStop(1, "rgba(0,0,0,0)");
      ctx.fillStyle = atmo; ctx.beginPath(); ctx.arc(x, y, r * 1.45, 0, Math.PI * 2); ctx.fill();

      var shade = ctx.createRadialGradient(x - r * 0.35, y - r * 0.35, r * 0.08, x, y, r);
      shade.addColorStop(0, "rgba(255,255,255," + (0.6 * alpha) + ")");
      shade.addColorStop(0.4, rgba(rgb, 0.92 * alpha));
      shade.addColorStop(1, rgba(rgb, 0.2 * alpha));
      ctx.fillStyle = shade; ctx.beginPath(); ctx.arc(x, y, r, 0, Math.PI * 2); ctx.fill();

      if (planet.gas) {
        ctx.save(); ctx.translate(x, y); ctx.rotate(planet.spin * 0.3);
        for (var band = 0; band < 4; band += 1) {
          ctx.fillStyle = "rgba(255,255,255," + (0.06 * alpha) + ")";
          ctx.fillRect(-r, -r * 0.5 + band * r * 0.28, r * 2, r * 0.1);
        }
        ctx.restore();
      }

      ctx.save(); ctx.beginPath(); ctx.arc(x, y, r * 0.96, 0, Math.PI * 2); ctx.clip();
      if (planet.logo && logoCache[planet.logo] && logoCache[planet.logo].complete && logoCache[planet.logo].naturalWidth) {
        var sz = r * 1.4;
        ctx.drawImage(logoCache[planet.logo], x - sz / 2, y - sz / 2, sz, sz);
      } else if (planet.label) {
        ctx.fillStyle = "rgba(255,255,255," + (0.9 * alpha) + ")";
        ctx.font = "bold " + Math.max(7, Math.round(r * 0.72)) + "px system-ui,sans-serif";
        ctx.textAlign = "center"; ctx.textBaseline = "middle";
        ctx.fillText(planet.label.length > 5 ? planet.label.slice(0, 4) : planet.label, x, y);
      }
      var highlight = ctx.createLinearGradient(x - r, y - r, x + r, y + r);
      highlight.addColorStop(0, "rgba(255,255,255,0)");
      highlight.addColorStop(0.45 + Math.sin(planet.spin) * 0.1, "rgba(255,255,255," + (0.15 * alpha) + ")");
      highlight.addColorStop(1, "rgba(0,0,0,0)");
      ctx.fillStyle = highlight; ctx.fillRect(x - r, y - r, r * 2, r * 2);
      ctx.restore();

      var lightAng = Math.atan2(sunY - y, sunX - x);
      ctx.strokeStyle = rgba(rgb, 0.5 * alpha); ctx.lineWidth = 1.2;
      ctx.beginPath(); ctx.arc(x, y, r, lightAng - 0.8, lightAng + 0.8); ctx.stroke();
    }

    function drawPlanetRing(x, y, radius, scale, alpha, color) {
      var r = radius * scale;
      ctx.save(); ctx.translate(x, y); ctx.scale(1, 0.26);
      var ringGrad = ctx.createLinearGradient(-r * 2, 0, r * 2, 0);
      ringGrad.addColorStop(0, "rgba(255,255,255,0)");
      ringGrad.addColorStop(0.5, rgba(hexToRgb(color), 0.55 * alpha));
      ringGrad.addColorStop(1, "rgba(255,255,255,0)");
      ctx.strokeStyle = ringGrad; ctx.lineWidth = 4;
      ctx.beginPath(); ctx.arc(0, 0, r * 1.6, 0, Math.PI * 2); ctx.stroke();
      ctx.restore();
    }

    function drawMoons(planet, pos, now, alpha) {
      for (var m = 0; m < planet.moons; m += 1) {
        var ma = now * 0.0035 + m * 2.1 + planet.angle;
        var mr = planet.radius * pos.scale * (1.9 + m * 0.75);
        var mx = pos.x + Math.cos(ma) * mr, my = pos.y + Math.sin(ma) * mr * 0.48;
        ctx.fillStyle = "rgba(200,220,255," + (0.75 * alpha) + ")";
        ctx.beginPath(); ctx.arc(mx, my, 2 + m * 0.6, 0, Math.PI * 2); ctx.fill();
      }
    }

    function drawSolarSystem(cx, cy, scale, alpha, now, planetSet, showLabels) {
      var th = theme();
      drawSun(cx, cy, scale, alpha, now);
      planetSet.forEach(function (p) { drawOrbitPath(cx, cy, p.orbit * scale, p.tilt, 0.08 * alpha, th.primary); });

      var positions = planetSet.map(function (p) {
        p.angle += p.speed * 16;
        return { planet: p, pos: orbitPos(cx, cy, p.orbit * scale, p.angle, p.tilt) };
      });
      positions.sort(function (a, b) { return a.pos.z - b.pos.z; });

      positions.forEach(function (item) {
        var p = item.planet, pos = item.pos;
        if (p.ring) drawPlanetRing(pos.x, pos.y, p.radius, pos.scale, pos.alpha * alpha, p.color);
        drawPlanetBody(pos.x, pos.y, p, pos, pos.alpha * alpha, cx, cy);
        drawMoons(p, pos, now, pos.alpha * alpha);
        if (showLabels && mode === "desktop" && pos.z > 0.15) {
          ctx.fillStyle = rgba(th.particle, 0.6 * pos.alpha * alpha);
          ctx.font = Math.round(8 * scale) + "px ui-monospace,monospace";
          ctx.textAlign = "center";
          ctx.fillText(p.label, pos.x, pos.y + p.radius * pos.scale + 14);
        }
      });
    }

    function drawAsteroidBelt(cx, cy, scale, alpha, now) {
      var th = theme();
      asteroids.forEach(function (a) {
        a.angle += a.speed * 14;
        var pos = orbitPos(cx, cy, (a.orbit + Math.sin(now * 0.002 + a.wobble) * 4) * scale, a.angle, 0.42);
        ctx.fillStyle = rgba(th.particle, 0.4 * alpha * pos.alpha);
        ctx.beginPath(); ctx.arc(pos.x, pos.y, a.size, 0, Math.PI * 2); ctx.fill();
      });
    }

    function drawMiniSystems(now) {
      var pf = profile();
      for (var i = 0; i < Math.min(pf.miniSystems, MINI_SYSTEMS.length); i += 1) {
        var ms = MINI_SYSTEMS[i];
        var mcx = width * ms.x + (smooth.x / width - 0.5) * 12;
        var mcy = height * ms.y + scrollY * 0.08;
        var mini = [];
        for (var p = 0; p < ms.planets; p += 1) {
          mini.push({ label: "", orbit: (38 + p * 22) * ms.scale, speed: 0.0003 + p * 0.00006, radius: 4 + p * 1.2, color: p % 2 === 0 ? "#60a5fa" : "#a78bfa", logo: null, ring: p === 2, moons: 0, gas: false, angle: p * 1.4 + ms.hue, tilt: 0.4, spin: 0 });
        }
        ctx.globalAlpha = 0.5;
        drawSolarSystem(mcx, mcy, ms.scale * 0.9, 0.65, now, mini, false);
        ctx.globalAlpha = 1;
      }
    }

    function drawAlienUfo(a, now, th, parX, parY) {
      var wobble = Math.sin(now * 0.004 + a.phase) * 6;
      var x = a.x + parX * 0.3, y = a.y + wobble + parY * 0.3;
      var s = a.scale * (mode === "mobile" ? 0.7 : 1);
      ctx.save(); ctx.translate(x, y); ctx.scale(s, s);

      var beam = 0.35 + Math.sin(now * 0.006 + a.blink) * 0.25;
      var beamGrad = ctx.createLinearGradient(0, 8, 0, 55);
      beamGrad.addColorStop(0, rgba(th.accent, 0.25 * beam));
      beamGrad.addColorStop(1, "rgba(0,0,0,0)");
      ctx.fillStyle = beamGrad;
      ctx.beginPath(); ctx.moveTo(-8, 8); ctx.lineTo(8, 8); ctx.lineTo(0, 55); ctx.closePath(); ctx.fill();

      ctx.fillStyle = "rgba(180,200,220,0.85)";
      ctx.beginPath(); ctx.ellipse(0, -2, 22, 7, 0, 0, Math.PI * 2); ctx.fill();
      ctx.fillStyle = "rgba(120,220,255,0.7)";
      ctx.beginPath(); ctx.ellipse(0, -8, 10, 8, 0, 0, Math.PI * 2); ctx.fill();
      ctx.strokeStyle = "rgba(255,255,255,0.4)"; ctx.lineWidth = 1;
      ctx.beginPath(); ctx.ellipse(0, -2, 22, 7, 0, 0, Math.PI * 2); ctx.stroke();

      for (var li = 0; li < 5; li += 1) {
        var blink = Math.sin(now * 0.01 + a.blink + li * 1.3) > 0.2;
        ctx.fillStyle = blink ? "rgba(100,255,180,0.95)" : "rgba(60,120,90,0.5)";
        ctx.beginPath(); ctx.arc(-16 + li * 8, 2, 2.2, 0, Math.PI * 2); ctx.fill();
      }
      ctx.restore();
    }

    function drawAlienFloater(a, now, parX, parY) {
      var x = a.baseX + Math.sin(now * a.speed * 2 + a.phase) * a.driftR + parX * 0.5;
      var y = a.baseY + Math.cos(now * a.speed * 1.6 + a.phase) * a.driftR * 0.6 + parY * 0.5;
      var s = a.scale * (mode === "mobile" ? 0.65 : 1);
      var bob = Math.sin(now * 0.003 + a.phase) * 4;
      ctx.save(); ctx.translate(x, y + bob); ctx.scale(s, s);

      ctx.fillStyle = a.color;
      ctx.beginPath(); ctx.ellipse(0, 2, 8, 12, 0, 0, Math.PI * 2); ctx.fill();
      ctx.beginPath(); ctx.arc(0, -10, 11, 0, Math.PI * 2); ctx.fill();

      ctx.fillStyle = "rgba(255,255,255,0.92)";
      ctx.beginPath(); ctx.ellipse(-5, -11, 4.5, 6, -0.2, 0, Math.PI * 2); ctx.fill();
      ctx.beginPath(); ctx.ellipse(5, -11, 4.5, 6, 0.2, 0, Math.PI * 2); ctx.fill();
      ctx.fillStyle = "rgba(10,10,30,0.9)";
      ctx.beginPath(); ctx.arc(-5, -10, 2.2, 0, Math.PI * 2); ctx.fill();
      ctx.beginPath(); ctx.arc(5, -10, 2.2, 0, Math.PI * 2); ctx.fill();

      var wave = Math.sin(now * 0.005 + a.phase) * 0.4;
      ctx.strokeStyle = a.color; ctx.lineWidth = 2.5; ctx.lineCap = "round";
      ctx.beginPath(); ctx.moveTo(-12, 0); ctx.quadraticCurveTo(-18, -8 + wave * 10, -22, -2); ctx.stroke();
      ctx.beginPath(); ctx.moveTo(12, 0); ctx.quadraticCurveTo(18, -8 - wave * 10, 22, -2); ctx.stroke();
      ctx.restore();
    }

    function drawSpaceJelly(a, now, th, parX, parY) {
      var x = a.baseX + Math.sin(now * a.speed + a.phase) * a.driftR * 0.5 + parX * 0.4;
      var y = a.baseY + Math.cos(now * a.speed * 0.8 + a.phase) * a.driftR * 0.4 + parY * 0.4;
      var s = a.scale * (mode === "mobile" ? 0.6 : 1);
      ctx.save(); ctx.translate(x, y); ctx.scale(s, s); ctx.globalAlpha = 0.55;

      var bell = ctx.createRadialGradient(0, -5, 2, 0, 0, 18);
      bell.addColorStop(0, rgba(th.secondary, 0.5));
      bell.addColorStop(1, rgba(th.primary, 0.05));
      ctx.fillStyle = bell;
      ctx.beginPath(); ctx.arc(0, 0, 16, Math.PI, 0); ctx.lineTo(16, 8); ctx.quadraticCurveTo(0, 14, -16, 8); ctx.closePath(); ctx.fill();

      for (var t = 0; t < 6; t += 1) {
        var sway = Math.sin(now * 0.004 + a.phase + t) * 5;
        ctx.strokeStyle = rgba(th.accent, 0.35);
        ctx.lineWidth = 1.5;
        ctx.beginPath(); ctx.moveTo(-12 + t * 4.8, 8);
        ctx.quadraticCurveTo(-12 + t * 4.8 + sway, 28 + t * 3, -10 + t * 4.8 + sway * 0.5, 40 + t * 2);
        ctx.stroke();
      }
      ctx.globalAlpha = 1; ctx.restore();
    }

    function drawAlienScout(a, now, th, parX, parY, sunCenter) {
      var orbit = 120 + a.driftR * 2;
      var ang = now * a.speed * 3 + a.phase;
      var x = sunCenter.x + Math.cos(ang) * orbit + parX * 0.2;
      var y = sunCenter.y + Math.sin(ang) * orbit * 0.35 + parY * 0.2;
      var s = a.scale * 0.55;
      ctx.save(); ctx.translate(x, y); ctx.scale(s, s);
      ctx.fillStyle = rgba(th.glow, 0.8);
      ctx.beginPath(); ctx.ellipse(0, 0, 6, 3, ang, 0, Math.PI * 2); ctx.fill();
      ctx.fillStyle = "rgba(180,255,200,0.9)";
      ctx.beginPath(); ctx.arc(0, -4, 3, 0, Math.PI * 2); ctx.fill();
      ctx.fillStyle = "rgba(20,20,40,0.9)";
      ctx.beginPath(); ctx.arc(-1.2, -4.5, 1, 0, Math.PI * 2); ctx.arc(1.2, -4.5, 1, 0, Math.PI * 2); ctx.fill();
      ctx.restore();
    }

    function drawAliens(now) {
      var th = theme(), pf = profile(), sun = solarCenter(pf);
      var parX = (smooth.x / Math.max(1, width) - 0.5) * 20;
      var parY = (smooth.y / Math.max(1, height) - 0.5) * 16;

      aliens.forEach(function (a) {
        if (a.type === "ufo") {
          a.x += a.vx;
          a.y = a.pathY + Math.sin(now * 0.002 + a.phase) * 12;
          if (a.x < -80) { a.x = width + 80; a.vx = Math.abs(a.vx); a.pathY = rand(height * 0.1, height * 0.85); }
          if (a.x > width + 80) { a.x = -80; a.vx = -Math.abs(a.vx); a.pathY = rand(height * 0.1, height * 0.85); }
          drawAlienUfo(a, now, th, parX, parY);
        } else if (a.type === "floater") {
          drawAlienFloater(a, now, parX, parY);
        } else if (a.type === "jelly") {
          drawSpaceJelly(a, now, th, parX, parY);
        } else if (a.type === "scout") {
          drawAlienScout(a, now, th, parX, parY, sun);
        }
      });
    }

    function drawComets(now) {
      var th = theme();
      if (now - lastComet > 2500 + Math.random() * 3000) { spawnComet(comets, width, height); lastComet = now; }
      if (mode === "desktop" && Math.random() < 0.0025) spawnComet(comets, width, height);
      comets.forEach(function (c) {
        if (!c.active) return;
        c.x += c.vx; c.y += c.vy; c.life -= c.decay;
        if (c.life <= 0 || c.x < -200 || c.x > width + 200 || c.y > height + 100) { c.active = false; return; }
        var ang = Math.atan2(c.vy, c.vx);
        var grad = ctx.createLinearGradient(c.x - Math.cos(ang) * c.tail * c.life, c.y - Math.sin(ang) * c.tail * c.life, c.x, c.y);
        grad.addColorStop(0, "rgba(255,255,255,0)");
        grad.addColorStop(0.4, rgba(th.primary, c.life * 0.35));
        grad.addColorStop(1, rgba(th.glow, c.life));
        ctx.strokeStyle = grad; ctx.lineWidth = 2.5;
        ctx.beginPath(); ctx.moveTo(c.x - Math.cos(ang) * c.tail * c.life, c.y - Math.sin(ang) * c.tail * c.life); ctx.lineTo(c.x, c.y); ctx.stroke();
      });
    }

    function drawHorizonGrid(now) {
      if (mode === "mobile") return;
      var th = theme(), cx = width * 0.5 + (smooth.x / width - 0.5) * 16;
      var horizon = height * 0.62 + scrollY * 0.04, t = (now * 0.00028) % 1;
      ctx.save(); ctx.globalCompositeOperation = "screen";
      for (var ring = 0; ring < 16; ring += 1) {
        var d = (ring + t) / 16, y = horizon + d * d * height * 0.5;
        ctx.strokeStyle = rgba(th.secondary, (1 - d) * 0.1);
        ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(width, y); ctx.stroke();
      }
      for (var i = 0; i < 24; i += 1) {
        var xn = i / 24 - 0.5;
        ctx.strokeStyle = rgba(th.primary, 0.06);
        ctx.beginPath(); ctx.moveTo(cx + xn * width * 1.3, horizon); ctx.lineTo(cx + xn * width * 0.06, height + 30); ctx.stroke();
      }
      ctx.restore();
    }

    function drawVignette() {
      var v = ctx.createRadialGradient(width * 0.5, height * 0.46, Math.min(width, height) * 0.15, width * 0.5, height * 0.5, Math.max(width, height) * 0.85);
      v.addColorStop(0, "rgba(0,0,0,0)");
      v.addColorStop(0.55, "rgba(0,0,0,0.1)");
      v.addColorStop(1, "rgba(0,0,0,0.58)");
      ctx.fillStyle = v; ctx.fillRect(0, 0, width, height);
    }

    function drawMainSolar(now) {
      var pf = profile(), center = solarCenter(pf);
      drawAsteroidBelt(center.x, center.y, pf.sysScale, 1, now);
      drawSolarSystem(center.x, center.y, pf.sysScale, 1, now, planets, true);
    }

    function frame(now) {
      updatePointer();
      drawCosmos();
      drawGalaxy(now);
      drawNebulae(now);
      drawWormholes(now);
      drawHorizonGrid(now);
      drawWarpStars(now);
      drawAliens(now);
      drawMiniSystems(now);
      drawMainSolar(now);
      drawComets(now);
      drawVignette();
      requestAnimationFrame(frame);
    }

    window.addEventListener("resize", resize, { passive: true });
    window.addEventListener("scroll", function () { scrollY = (window.scrollY || 0) * 0.04; }, { passive: true });
    window.addEventListener("mousemove", function (e) { pointer.x = e.clientX; pointer.y = e.clientY; }, { passive: true });

    pointer.x = (window.innerWidth || 0) * 0.5;
    pointer.y = (window.innerHeight || 0) * 0.5;
    smooth.x = pointer.x; smooth.y = pointer.y;
    resize();
    requestAnimationFrame(frame);
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init, { once: true });
  } else {
    init();
  }
})();
