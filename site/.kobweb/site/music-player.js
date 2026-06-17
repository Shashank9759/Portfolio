(function () {
  "use strict";

  var YT_VIDEO_ID = "n2dVFdqMYGA";
  var SKIP_START_SEC = 12;
  var DANCE_BPM = 118;
  var DANCE_BPM_IDLE = 92;
  var BHANGRA_FRAMES = 16;
  var CHAR_FEET_Y = 76;
  var CHAR_TOP_Y = 52;
  var CHAR_WIDTH = 132;
  var CHAR_HEIGHT = 138;

  window.__portfolioMusic = { playing: false, volume: 45, beat: 0, muted: true, autoplaying: false };

  function dispatch() {
    document.dispatchEvent(new CustomEvent("portfolio-music", {
      detail: {
        playing: window.__portfolioMusic.playing,
        volume: window.__portfolioMusic.volume,
        beat: window.__portfolioMusic.beat,
        muted: window.__portfolioMusic.muted,
      },
    }));
  }

  function clamp(v, a, b) { return Math.max(a, Math.min(b, v)); }

  function loadYouTubeApi(cb) {
    if (window.YT && window.YT.Player) {
      cb();
      return;
    }
    var prev = window.onYouTubeIframeAPIReady;
    window.onYouTubeIframeAPIReady = function () {
      if (typeof prev === "function") prev();
      cb();
    };
    if (!document.querySelector('script[src*="youtube.com/iframe_api"]')) {
      var tag = document.createElement("script");
      tag.src = "https://www.youtube.com/iframe_api";
      tag.async = true;
      document.head.appendChild(tag);
    }
  }

  function initPlayer() {
    var playBtn = document.getElementById("music-play");
    var volInput = document.getElementById("music-volume");
    var volIcon = document.querySelector(".music-vol-icon");
    var widget = document.getElementById("music-widget");
    var statusEl = document.getElementById("music-status");
    var mountEl = document.getElementById("youtube-mount");
    if (!playBtn || !volInput || !mountEl) return;

    var player = null;
    var playerReady = false;
    var userWantsSound = false;
    var soundUnlocked = false;
    var pendingUnlock = false;
    var dancerZone = document.querySelector(".dancer-zone");
    var dancerCanvas = document.getElementById("dancer-canvas");
    var dCtx = dancerCanvas ? dancerCanvas.getContext("2d") : null;
    var danceFrame = 0;
    var lastBeat = 0;

    setStatus("Loading player…");

    function setStatus(msg) {
      if (statusEl) statusEl.textContent = msg || "";
    }

    function setIcon(playing) {
      playBtn.innerHTML = '<span class="material-symbols-outlined">' + (playing ? "pause" : "play_arrow") + "</span>";
      widget.classList.toggle("is-playing", playing);
    }

    function setVolumeIcon(v) {
      if (!volIcon) return;
      if (window.__portfolioMusic.muted || v === 0) volIcon.textContent = "volume_off";
      else if (v < 40) volIcon.textContent = "volume_down";
      else volIcon.textContent = "volume_up";
    }

    function applyVolume(v) {
      var vol = clamp(v, 0, 100);
      window.__portfolioMusic.volume = vol;
      volInput.value = String(vol);
      setVolumeIcon(vol);
      if (!playerReady) return;
      player.setVolume(vol);
      if (vol === 0) {
        player.mute();
        window.__portfolioMusic.muted = true;
      } else if (userWantsSound) {
        player.unMute();
        window.__portfolioMusic.muted = false;
      }
      dispatch();
    }

    function syncUiFromState(state) {
      var playing = state === 1;
      var buffering = state === 3;
      window.__portfolioMusic.playing = playing;
      window.__portfolioMusic.autoplaying = playing || buffering;
      setIcon(playing);
      if (playing) setStatus("");
      dispatch();
    }

    function resetToMusicStart() {
      if (!playerReady) return;
      try {
        player.seekTo(SKIP_START_SEC, true);
      } catch (e) { /* ignore */ }
    }

    function beginAudiblePlayback() {
      if (soundUnlocked) return;
      if (!playerReady) {
        pendingUnlock = true;
        setStatus("Starting…");
        return;
      }
      soundUnlocked = true;
      pendingUnlock = false;
      userWantsSound = true;
      try {
        player.pauseVideo();
        resetToMusicStart();
        player.unMute();
        player.setVolume(window.__portfolioMusic.volume);
        window.__portfolioMusic.muted = false;
        setVolumeIcon(window.__portfolioMusic.volume);
        window.setTimeout(function () {
          if (!playerReady) return;
          resetToMusicStart();
          player.playVideo();
          setStatus("");
          dispatch();
        }, 120);
      } catch (e) {
        setStatus("Tap play to start");
      }
    }

    function prepareAtMusicStart() {
      if (!playerReady) return;
      try {
        player.mute();
        window.__portfolioMusic.muted = true;
        resetToMusicStart();
        player.pauseVideo();
      } catch (e) { /* ignore */ }
    }

    function startPlayback(withSound) {
      if (!playerReady) return;
      if (withSound) beginAudiblePlayback();
      else prepareAtMusicStart();
    }

    function togglePlay() {
      if (!playerReady) {
        setStatus("Loading player…");
        return;
      }
      var state = player.getPlayerState();
      if (state === 1) {
        player.pauseVideo();
        window.__portfolioMusic.playing = false;
        window.__portfolioMusic.autoplaying = false;
        setIcon(false);
        dispatch();
      } else {
        if (!soundUnlocked) beginAudiblePlayback();
        else {
          player.unMute();
          player.setVolume(window.__portfolioMusic.volume);
          window.__portfolioMusic.muted = false;
          player.playVideo();
          setVolumeIcon(window.__portfolioMusic.volume);
          dispatch();
        }
      }
    }

    loadYouTubeApi(function () {
      var origin = window.location.origin;
      if (!origin || origin === "null") origin = "http://localhost:3000";

      player = new window.YT.Player(mountEl, {
        height: "200",
        width: "200",
        videoId: YT_VIDEO_ID,
        playerVars: {
          autoplay: 0,
          mute: 1,
          start: SKIP_START_SEC,
          controls: 0,
          disablekb: 1,
          fs: 0,
          modestbranding: 1,
          rel: 0,
          playsinline: 1,
          enablejsapi: 1,
          origin: origin,
        },
        events: {
          onReady: function () {
            playerReady = true;
            var iframe = mountEl.querySelector("iframe");
            if (iframe) iframe.setAttribute("allow", "autoplay; encrypted-media");
            applyVolume(parseInt(volInput.value, 10) || 45);
            prepareAtMusicStart();
            setStatus("Tap anywhere for Sahiba");
            if (pendingUnlock) beginAudiblePlayback();
          },
          onStateChange: function (e) {
            syncUiFromState(e.data);
          },
          onError: function (e) {
            widget.classList.add("has-error");
            var code = e && e.data;
            if (code === 101 || code === 150) setStatus("Video blocked — open YouTube");
            else setStatus("Playback error — tap play");
          },
        },
      });
    });

    playBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      if (!soundUnlocked) {
        beginAudiblePlayback();
        return;
      }
      togglePlay();
    });

    function unlockOnFirstInteraction() {
      if (soundUnlocked) return;
      beginAudiblePlayback();
    }

    document.addEventListener("pointerdown", unlockOnFirstInteraction, { capture: true, passive: true });
    document.addEventListener("touchstart", unlockOnFirstInteraction, { capture: true, passive: true });

    volInput.addEventListener("input", function () {
      userWantsSound = true;
      applyVolume(parseInt(volInput.value, 10));
      if (!soundUnlocked) beginAudiblePlayback();
      else if (playerReady && player.getPlayerState() !== 1) {
        player.unMute();
        player.playVideo();
      }
    });

    var MUSIC_BOTTOM_RESERVE = 112;

    function zoneSize() {
      var rect = dancerZone ? dancerZone.getBoundingClientRect() : null;
      return {
        w: rect && rect.width > 0 ? rect.width : Math.min(window.innerWidth * 0.38, 360),
        h: rect && rect.height > 0 ? rect.height : window.innerHeight - 88,
      };
    }

    function updateDancerBounds() {
      if (!dancerZone) return;
      var clipBottom = MUSIC_BOTTOM_RESERVE;
      var footer = document.querySelector(".site-footer");

      if (footer) {
        var top = footer.getBoundingClientRect().top;
        if (top < window.innerHeight - MUSIC_BOTTOM_RESERVE) {
          clipBottom = Math.max(MUSIC_BOTTOM_RESERVE, window.innerHeight - top + 16);
        }
      }

      dancerZone.style.bottom = clipBottom + "px";
      var height = dancerZone.getBoundingClientRect().height;
      dancerZone.classList.toggle("is-hidden", height < 140);
    }

    function resizeDancer() {
      if (!dancerCanvas) return;
      var size = zoneSize();
      var dpr = Math.min(2, window.devicePixelRatio || 1);
      dancerCanvas.width = Math.floor(size.w * dpr);
      dancerCanvas.height = Math.floor(size.h * dpr);
      dancerCanvas.style.width = size.w + "px";
      dancerCanvas.style.height = size.h + "px";
      if (dCtx) dCtx.setTransform(dpr, 0, 0, dpr, 0, 0);
    }

    function limb(x1, y1, x2, y2, w, color) {
      dCtx.strokeStyle = color || "#0f172a";
      dCtx.lineWidth = w;
      dCtx.lineCap = "round";
      dCtx.lineJoin = "round";
      dCtx.beginPath();
      dCtx.moveTo(x1, y1);
      dCtx.lineTo(x2, y2);
      dCtx.stroke();
    }

    function roundRect(x, y, rw, rh, r) {
      dCtx.beginPath();
      dCtx.moveTo(x + r, y);
      dCtx.arcTo(x + rw, y, x + rw, y + rh, r);
      dCtx.arcTo(x + rw, y + rh, x, y + rh, r);
      dCtx.arcTo(x, y + rh, x, y, r);
      dCtx.arcTo(x, y, x + rw, y, r);
      dCtx.closePath();
    }

    function armPoint(shoulderX, shoulderY, angle, len) {
      return { x: shoulderX + Math.cos(angle) * len, y: shoulderY + Math.sin(angle) * len };
    }

    function lerp(a, b, t) { return a + (b - a) * t; }

    function lerpPose(a, b, t) {
      return {
        armL: lerp(a.armL, b.armL, t),
        armR: lerp(a.armR, b.armR, t),
        legL: lerp(a.legL, b.legL, t),
        legR: lerp(a.legR, b.legR, t),
        squat: lerp(a.squat, b.squat, t),
        tilt: lerp(a.tilt, b.tilt, t),
        hop: lerp(a.hop, b.hop, t),
        lean: lerp(a.lean, b.lean, t),
      };
    }

    function easeSnap(t) {
      return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
    }

    function getBhangraPose(frame) {
      var up = -Math.PI / 2;
      var poses = [
        { armL: up - 0.35, armR: up + 0.35, legL: 0.15, legR: -0.15, squat: 0, tilt: 0, hop: 0, lean: 0 },
        { armL: up - 0.1, armR: -0.2, legL: 1.15, legR: -0.35, squat: 14, tilt: 0.1, hop: 6, lean: 0.08 },
        { armL: -0.15, armR: Math.PI + 0.15, legL: 0.55, legR: -0.55, squat: 6, tilt: 0, hop: 0, lean: 0 },
        { armL: up - 0.5, armR: up + 0.5, legL: -0.25, legR: 1.2, squat: 20, tilt: -0.12, hop: 10, lean: -0.1 },
        { armL: -2.35, armR: -0.45, legL: 0.95, legR: -0.15, squat: 26, tilt: 0.14, hop: 4, lean: 0.12 },
        { armL: up - 0.25, armR: up + 0.25, legL: 0.35, legR: -0.35, squat: 0, tilt: 0, hop: 14, lean: 0 },
        { armL: -0.35, armR: Math.PI + 0.35, legL: -0.4, legR: 1.05, squat: 12, tilt: -0.08, hop: 8, lean: -0.06 },
        { armL: up - 0.6, armR: -2.1, legL: 0.75, legR: -0.75, squat: 18, tilt: 0.16, hop: 0, lean: 0.14 },
        { armL: -2.5, armR: -2.5, legL: 0.25, legR: -0.25, squat: 30, tilt: 0, hop: 0, lean: 0 },
        { armL: up - 0.15, armR: Math.PI + 0.15, legL: 1.25, legR: -1.05, squat: 8, tilt: 0.1, hop: 12, lean: 0.08 },
        { armL: -2.2, armR: -0.3, legL: -0.55, legR: 1.35, squat: 22, tilt: -0.14, hop: 6, lean: -0.12 },
        { armL: up - 0.4, armR: up + 0.4, legL: 0.45, legR: -0.45, squat: 4, tilt: 0, hop: 16, lean: 0 },
        { armL: -0.5, armR: Math.PI + 0.5, legL: 0.85, legR: -0.85, squat: 24, tilt: 0.06, hop: 0, lean: 0.05 },
        { armL: up - 0.55, armR: -2.4, legL: 1.05, legR: -0.25, squat: 16, tilt: 0.12, hop: 8, lean: 0.1 },
        { armL: -2.45, armR: -2.45, legL: 0.2, legR: -0.2, squat: 32, tilt: 0, hop: 0, lean: 0 },
        { armL: up - 0.2, armR: up + 0.2, legL: -0.35, legR: 1.15, squat: 10, tilt: -0.1, hop: 10, lean: -0.08 },
      ];
      return poses[frame % BHANGRA_FRAMES];
    }

    function fitScale(w, h) {
      var padX = 8;
      var padY = 12;
      return Math.min((w - padX * 2) / CHAR_WIDTH, (h - padY * 2) / CHAR_HEIGHT, 3.35);
    }

    function drawBhangraDeveloper(now, w, h) {
      if (!dCtx) return;
      dCtx.clearRect(0, 0, w, h);

      var musicOn = window.__portfolioMusic.playing;
      var vol = musicOn ? window.__portfolioMusic.volume / 100 : 0.6;
      var bpm = musicOn ? DANCE_BPM : DANCE_BPM_IDLE;
      var beatMs = 60000 / bpm;
      var stepMs = musicOn ? beatMs * 0.5 : beatMs * 0.65;

      if (now - lastBeat > stepMs) {
        lastBeat = now;
        danceFrame = (danceFrame + 1) % BHANGRA_FRAMES;
        window.__portfolioMusic.beat = danceFrame;
        dispatch();
      }

      var frameT = clamp((now - lastBeat) / stepMs, 0, 1);
      var poseA = getBhangraPose(danceFrame);
      var poseB = getBhangraPose(danceFrame + 1);
      var pose = lerpPose(poseA, poseB, easeSnap(frameT));

      var scale = fitScale(w, h);
      var baseX = w * 0.5 + pose.lean * 10 * scale;
      var footY = h - 14;
      var bounce = Math.abs(Math.sin(now * 0.013)) * (musicOn ? 22 * vol : 8);
      var shimmy = Math.sin(now * 0.026) * (musicOn ? 10 : 4);
      var headBob = Math.sin(now * 0.039) * (musicOn ? 5 : 2);

      dCtx.save();
      dCtx.translate(baseX + shimmy, footY - CHAR_FEET_Y * scale - bounce - pose.hop * scale * 0.22);
      dCtx.scale(scale, scale);
      dCtx.globalAlpha = 0.88;

      dCtx.translate(0, pose.squat);

      if (musicOn) {
        var glow = dCtx.createRadialGradient(0, 10, 8, 0, 20, 95);
        glow.addColorStop(0, "rgba(251,191,36,0.22)");
        glow.addColorStop(0.5, "rgba(52,211,153,0.12)");
        glow.addColorStop(1, "rgba(0,0,0,0)");
        dCtx.fillStyle = glow;
        dCtx.beginPath();
        dCtx.ellipse(0, 50, 72, 24, 0, 0, Math.PI * 2);
        dCtx.fill();
      }

      dCtx.save();
      dCtx.rotate(pose.tilt);

      var hipY = 24, shoulderY = -12 + headBob;
      var armLen = musicOn ? 46 : 40;
      var lax = armPoint(-16, shoulderY, pose.armL, armLen);
      var rax = armPoint(16, shoulderY, pose.armR, armLen);
      var lkx = -18 + pose.legL * 26, lky = hipY + 52 - Math.abs(pose.legL) * 10;
      var rkx = 18 + pose.legR * 26, rky = hipY + 52 - Math.abs(pose.legR) * 10;

      limb(-14, hipY, lkx, lky, 11);
      limb(14, hipY, rkx, rky, 11);

      dCtx.fillStyle = "#1e3a5f";
      roundRect(-20, hipY - 2, 18, 50, 5); dCtx.fill();
      roundRect(2, hipY - 2, 18, 50, 5); dCtx.fill();
      dCtx.fillStyle = "#f8fafc";
      roundRect(-16, hipY + 42, 12, 8, 2); dCtx.fill();
      roundRect(4, hipY + 42, 12, 8, 2); dCtx.fill();

      dCtx.fillStyle = "#0f172a";
      roundRect(-24, 2, 48, 28, 6); dCtx.fill();
      dCtx.fillStyle = "#1e293b";
      roundRect(-30, -8, 60, 42, 10); dCtx.fill();
      dCtx.fillStyle = "#f97316";
      dCtx.beginPath();
      dCtx.moveTo(-30, 8);
      dCtx.lineTo(30, 8);
      dCtx.lineTo(26, 22);
      dCtx.lineTo(-26, 22);
      dCtx.closePath();
      dCtx.fill();
      dCtx.fillStyle = "#34d399";
      dCtx.font = "bold 13px ui-monospace,monospace";
      dCtx.textAlign = "center";
      dCtx.fillText("</>", 0, 16);

      dCtx.fillStyle = "#fde68a";
      dCtx.beginPath();
      dCtx.arc(0, -28, 18, 0, Math.PI * 2);
      dCtx.fill();
      dCtx.fillStyle = "#ea580c";
      roundRect(-20, -42, 40, 12, 4);
      dCtx.fill();
      dCtx.fillStyle = "#fbbf24";
      dCtx.beginPath();
      dCtx.arc(0, -48, 8, 0, Math.PI * 2);
      dCtx.fill();

      if (musicOn && pose.hop > 8) {
        dCtx.fillStyle = "rgba(251,191,36,0.35)";
        dCtx.font = "bold 14px system-ui,sans-serif";
        dCtx.textAlign = "center";
        dCtx.fillText("🥳", 22, -52);
      }

      dCtx.fillStyle = "#1e293b";
      roundRect(-17, -34, 34, 9, 3); dCtx.fill();
      dCtx.fillStyle = "rgba(120,200,255,0.55)";
      roundRect(-15, -33, 12, 6, 2); dCtx.fill();
      roundRect(3, -33, 12, 6, 2); dCtx.fill();
      dCtx.fillStyle = "#0f172a";
      dCtx.beginPath();
      dCtx.arc(-6, -28, 3, 0, Math.PI * 2);
      dCtx.arc(6, -28, 3, 0, Math.PI * 2);
      dCtx.fill();

      dCtx.strokeStyle = "#475569";
      dCtx.lineWidth = 4;
      dCtx.beginPath();
      dCtx.arc(0, -24, 24, Math.PI * 1.05, Math.PI * 1.95);
      dCtx.stroke();

      limb(-16, shoulderY, lax.x, lax.y, 10);
      limb(16, shoulderY, rax.x, rax.y, 10);

      dCtx.fillStyle = "#fde68a";
      dCtx.beginPath();
      dCtx.arc(lax.x, lax.y, 7, 0, Math.PI * 2);
      dCtx.arc(rax.x, rax.y, 7, 0, Math.PI * 2);
      dCtx.fill();

      if (musicOn && (danceFrame % 4 < 2)) {
        dCtx.save();
        dCtx.translate(rax.x, rax.y);
        dCtx.rotate(pose.armR + 0.4);
        dCtx.fillStyle = "#334155";
        roundRect(-14, -10, 28, 20, 3); dCtx.fill();
        dCtx.fillStyle = "#38bdf8";
        roundRect(-11, -7, 22, 14, 2); dCtx.fill();
        dCtx.fillStyle = "#34d399";
        dCtx.font = "bold 8px ui-monospace,monospace";
        dCtx.textAlign = "center";
        dCtx.fillText("dev", 0, 3);
        dCtx.restore();
      }

      dCtx.restore();
      dCtx.restore();
    }

    function dancerLoop(now) {
      var size = zoneSize();
      drawBhangraDeveloper(now, size.w, size.h);
      requestAnimationFrame(dancerLoop);
    }

    function onViewportChange() {
      updateDancerBounds();
      resizeDancer();
    }

    window.addEventListener("resize", onViewportChange, { passive: true });
    window.addEventListener("scroll", onViewportChange, { passive: true });
    onViewportChange();
    requestAnimationFrame(dancerLoop);
    setVolumeIcon(parseInt(volInput.value, 10) || 45);
    setIcon(false);
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", initPlayer, { once: true });
  } else {
    initPlayer();
  }
})();
