(function () {
  "use strict";

  var PLAYLIST_VERSION = 4;
  var BLOCKED_KEY = "portfolio-music-blocked-v4";
  var VERIFIED_KEY = "portfolio-music-verified-v4";
  var VERSION_KEY = "portfolio-music-version-v4";
  var SEED_VERIFIED = ["n2dVFdqMYGA", "VNs_cCtdbPc", "dZ0fwJojhrs"];

  /** Verified catalog. `skip` = vocal start (seconds). `match` = title keywords for sanity checks. */
  var MASTER_PLAYLIST = [
    { id: "n2dVFdqMYGA", title: "Sahiba", artist: "Aditya Rikhari", skip: 12, bpm: 118, match: ["sahiba"] },
    { id: "MhXCj8E9CZU", title: "Samjho Na", artist: "Aditya Rikhari", skip: 5, bpm: 108, match: ["samjho"] },
    { id: "0PyHEaoZE1c", title: "Jaana Samjho Na", artist: "Aditya Rikhari", skip: 54, bpm: 110, match: ["jaana", "samjho"] },
    { id: "VNs_cCtdbPc", title: "Brown Munde", artist: "AP Dhillon", skip: 2, bpm: 92, match: ["brown munde"] },
    { id: "vX2cDW8LUWk", title: "Excuses", artist: "AP Dhillon", skip: 5, bpm: 92, match: ["excuses"] },
    { id: "mZQH8CPQ-wo", title: "With You", artist: "AP Dhillon", skip: 8, bpm: 87, match: ["with you"] },
    { id: "yzIyufV6ADk", title: "Majhail", artist: "AP Dhillon", skip: 13, bpm: 95, match: ["majhail"] },
    { id: "nqUN530Rgtw", title: "Summer High", artist: "AP Dhillon", skip: 4, bpm: 90, match: ["summer high"] },
    { id: "cqP8I5aaud8", title: "Insane", artist: "AP Dhillon", skip: 10, bpm: 96, match: ["insane"] },
    { id: "slM5s55Jz0k", title: "Thodi Si Daaru", artist: "AP Dhillon", skip: 14, bpm: 98, match: ["thodi"] },
    { id: "-K-WY0WRT48", title: "OLD MONEY", artist: "AP Dhillon", skip: 6, bpm: 94, match: ["old money"] },
    { id: "UkOPtbo73Ws", title: "Arrogant", artist: "AP Dhillon", skip: 8, bpm: 92, match: ["arrogant"] },
    { id: "7v0_uipNGao", title: "TOXIC", artist: "AP Dhillon", skip: 7, bpm: 97, match: ["toxic"] },
    { id: "6r4ckHlQH4c", title: "Real Talk", artist: "AP Dhillon", skip: 8, bpm: 92, match: ["real talk"] },
    { id: "ma_dtgS_W_w", title: "Without Me", artist: "AP Dhillon", skip: 8, bpm: 90, match: ["without me"] },
    { id: "0-67daqE4xU", title: "Lifestyle", artist: "AP Dhillon", skip: 8, bpm: 92, match: ["lifestyle"] },
    { id: "2FhgKp_lfJQ", title: "Afsos", artist: "AP Dhillon", skip: 10, bpm: 88, match: ["afsos"] },
    { id: "cl0a3i2wFcc", title: "G.O.A.T.", artist: "Diljit Dosanjh", skip: 8, bpm: 94, match: ["g.o.a.t", "goat"] },
    { id: "KX06ksuS6Xo", title: "Clash", artist: "Diljit Dosanjh", skip: 8, bpm: 96, match: ["clash"] },
    { id: "dCmp56tSSmA", title: "Born To Shine", artist: "Diljit Dosanjh", skip: 10, bpm: 94, match: ["born to shine"] },
    { id: "ZVgergj8Xe4", title: "Lemonade", artist: "Diljit Dosanjh", skip: 10, bpm: 92, match: ["lemonade"] },
    { id: "-mgGnx1p3b8", title: "Lalkaara", artist: "Diljit Dosanjh", skip: 10, bpm: 98, match: ["lalkaara", "lalkara"] },
    { id: "xB9-dsTC_0U", title: "Patiala Peg", artist: "Diljit Dosanjh", skip: 12, bpm: 100, match: ["patiala peg"] },
    { id: "cXUndHRKmXQ", title: "Peed", artist: "Diljit Dosanjh", skip: 12, bpm: 88, match: ["peed"] },
    { id: "P-DhwN87JDY", title: "Do You Know", artist: "Diljit Dosanjh", skip: 8, bpm: 96, match: ["do you know"] },
    { id: "dZ0fwJojhrs", title: "Lahore", artist: "Guru Randhawa", skip: 7, bpm: 98, match: ["lahore"] },
    { id: "cWMxCE2HTag", title: "Softly", artist: "Karan Aujla", skip: 11, bpm: 94, match: ["softly"] },
    { id: "4DfVxVeqk2o", title: "52 Bars", artist: "Karan Aujla", skip: 14, bpm: 96, match: ["52 bars"] },
    { id: "k85UB5b6pJU", title: "Admirin' You", artist: "Karan Aujla", skip: 9, bpm: 88, match: ["admirin"] },
    { id: "5X7WWVTrBvM", title: "Lamberghini", artist: "The Doorbeen", skip: 4, bpm: 100, match: ["lamberghini", "lamborghini"] },
    { id: "60ItHLz5WEA", title: "Faded", artist: "Alan Walker", skip: 44, bpm: 90, match: ["faded"] },
    { id: "JGwWNGJdvx8", title: "Shape of You", artist: "Ed Sheeran", skip: 8, bpm: 96, match: ["shape of you"] },
    { id: "kJQP7kiw5Fk", title: "Despacito", artist: "Luis Fonsi", skip: 15, bpm: 89, match: ["despacito"] },
    { id: "RgKAFK5djSk", title: "See You Again", artist: "Wiz Khalifa", skip: 47, bpm: 80, match: ["see you again"] },
    { id: "OPf0YbXqDm0", title: "Uptown Funk", artist: "Bruno Mars", skip: 28, bpm: 115, match: ["uptown funk"] },
    { id: "nfWlot6h_JM", title: "Shake It Off", artist: "Taylor Swift", skip: 0, bpm: 80, match: ["shake it off"] },
    { id: "hT_nvWreIhg", title: "Counting Stars", artist: "OneRepublic", skip: 0, bpm: 122, match: ["counting stars"] },
    { id: "7wtfhZwyrcc", title: "Believer", artist: "Imagine Dragons", skip: 0, bpm: 125, match: ["believer"] },
    { id: "RBumgq5yVrA", title: "Let Her Go", artist: "Passenger", skip: 0, bpm: 75, match: ["let her go"] },
    { id: "2Vv-BfVoq4g", title: "Perfect", artist: "Ed Sheeran", skip: 0, bpm: 63, match: ["perfect"] },
    { id: "09R8_2nJtjg", title: "Sugar", artist: "Maroon 5", skip: 0, bpm: 120, match: ["sugar"] },
    { id: "lp-EO5I60KA", title: "Thinking Out Loud", artist: "Ed Sheeran", skip: 0, bpm: 79, match: ["thinking out loud"] },
    { id: "fRh_vgS2dFE", title: "Sorry", artist: "Justin Bieber", skip: 0, bpm: 100, match: ["sorry"] },
    { id: "CevxZvSJLk8", title: "Roar", artist: "Katy Perry", skip: 0, bpm: 90, match: ["roar"] },
    { id: "YQHsXMglC9A", title: "Hello", artist: "Adele", skip: 15, bpm: 79, match: ["hello"] },
    { id: "0KSOMA3QBU0", title: "Dark Horse", artist: "Katy Perry", skip: 0, bpm: 132, match: ["dark horse"] },
    { id: "fJ9rUzIMcZQ", title: "Bohemian Rhapsody", artist: "Queen", skip: 0, bpm: 72, match: ["bohemian"] },
    { id: "eVTXPUF4Oz4", title: "In The End", artist: "Linkin Park", skip: 0, bpm: 105, match: ["in the end"] },
    { id: "kXYiU_JCYtU", title: "Numb", artist: "Linkin Park", skip: 0, bpm: 110, match: ["numb"] },
    { id: "VbfpW0pbvaU", title: "Stitches", artist: "Shawn Mendes", skip: 0, bpm: 102, match: ["stitches"] },
    { id: "1G4isv_Fylg", title: "Paradise", artist: "Coldplay", skip: 0, bpm: 140, match: ["paradise"] },
    { id: "kffacxfA7G4", title: "Baby", artist: "Justin Bieber", skip: 0, bpm: 65, match: ["baby"] },
  ];

  var BHANGRA_FRAMES = 16;
  var CHAR_FEET_Y = 76;
  var CHAR_TOP_EXTENT = 64;
  var CHAR_WIDTH = 132;
  var CHAR_HEIGHT = 138;

  window.__portfolioMusic = {
    playing: false,
    volume: 45,
    beat: 0,
    muted: true,
    autoplaying: false,
    trackIndex: 0,
    title: "",
    artist: "",
    bpm: 118,
  };

  function loadIdSet(key) {
    try {
      var raw = localStorage.getItem(key);
      if (!raw) return new Set();
      var arr = JSON.parse(raw);
      return new Set(Array.isArray(arr) ? arr : []);
    } catch (e) {
      return new Set();
    }
  }

  function saveIdSet(key, set) {
    try {
      localStorage.setItem(key, JSON.stringify(Array.from(set)));
    } catch (e) { /* ignore */ }
  }

  function initPlaylistStorage() {
    var blocked = loadIdSet(BLOCKED_KEY);
    var verified = loadIdSet(VERIFIED_KEY);
    var storedVer = parseInt(localStorage.getItem(VERSION_KEY) || "0", 10);
    if (storedVer !== PLAYLIST_VERSION) {
      localStorage.setItem(VERSION_KEY, String(PLAYLIST_VERSION));
      verified = new Set(SEED_VERIFIED);
      saveIdSet(VERIFIED_KEY, verified);
    }
    if (verified.size === 0) {
      SEED_VERIFIED.forEach(function (id) { verified.add(id); });
      saveIdSet(VERIFIED_KEY, verified);
    }
    return { blocked: blocked, verified: verified };
  }

  function dispatch() {
    document.dispatchEvent(new CustomEvent("portfolio-music", {
      detail: {
        playing: window.__portfolioMusic.playing,
        volume: window.__portfolioMusic.volume,
        beat: window.__portfolioMusic.beat,
        muted: window.__portfolioMusic.muted,
        trackIndex: window.__portfolioMusic.trackIndex,
        title: window.__portfolioMusic.title,
        artist: window.__portfolioMusic.artist,
        bpm: window.__portfolioMusic.bpm,
      },
    }));
  }

  function clamp(v, a, b) { return Math.max(a, Math.min(b, v)); }

  function trackTitleMatches(track, ytTitle) {
    if (!track || !track.match || !track.match.length) return true;
    var t = (ytTitle || "").toLowerCase();
    for (var i = 0; i < track.match.length; i++) {
      if (t.indexOf(track.match[i]) >= 0) return true;
    }
    return false;
  }

  function findTrackById(videoId) {
    for (var i = 0; i < MASTER_PLAYLIST.length; i++) {
      if (MASTER_PLAYLIST[i].id === videoId) return MASTER_PLAYLIST[i];
    }
    return null;
  }

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
    var titleEl = document.getElementById("music-title");
    var artistEl = document.getElementById("music-artist");
    var playlistBtn = document.getElementById("music-playlist-btn");
    var playlistEl = document.getElementById("music-playlist");
    var mountEl = document.getElementById("youtube-mount");
    var probeMountEl = document.getElementById("youtube-probe-mount");
    if (!playBtn || !volInput || !mountEl) return;

    var storage = initPlaylistStorage();
    var blockedIds = storage.blocked;
    var verifiedIds = storage.verified;
    var visibleTracks = [];

    var player = null;
    var playerReady = false;
    var probePlayer = null;
    var probeReady = false;
    var probeQueue = [];
    var probeCurrentId = null;
    var probeTimer = null;
    var probeMode = "discover";
    var probeTotal = 0;
    var probeDone = 0;
    var userWantsSound = false;
    var soundUnlocked = false;
    var pendingUnlock = false;
    var playlistOpen = false;
    var trackIndex = 0;
    var dancerZone = document.querySelector(".dancer-zone");
    var dancerCanvas = document.getElementById("dancer-canvas");
    var dCtx = dancerCanvas ? dancerCanvas.getContext("2d") : null;
    var danceFrame = 0;
    var lastBeat = 0;

    function getCurrentTrack() {
      if (visibleTracks.length === 0) return MASTER_PLAYLIST[0];
      return visibleTracks[trackIndex] || visibleTracks[0];
    }

    function pickRandomVisibleIndex(exclude) {
      if (visibleTracks.length <= 1) return 0;
      var idx;
      do {
        idx = Math.floor(Math.random() * visibleTracks.length);
      } while (idx === exclude);
      return idx;
    }

    function rebuildVisibleTracks() {
      visibleTracks = MASTER_PLAYLIST.filter(function (t) {
        return verifiedIds.has(t.id) && !blockedIds.has(t.id);
      });
      if (trackIndex >= visibleTracks.length) trackIndex = 0;
    }

    function persistBlocked() { saveIdSet(BLOCKED_KEY, blockedIds); }
    function persistVerified() { saveIdSet(VERIFIED_KEY, verifiedIds); }

    function markTrackVerified(videoId) {
      if (!videoId || blockedIds.has(videoId) || verifiedIds.has(videoId)) return;
      verifiedIds.add(videoId);
      persistVerified();
      rebuildVisibleTracks();
      buildPlaylistUI();
    }

    function blockTrack(videoId) {
      if (!videoId) return;
      blockedIds.add(videoId);
      verifiedIds.delete(videoId);
      persistBlocked();
      persistVerified();
      var wasCurrent = getCurrentTrack().id === videoId;
      rebuildVisibleTracks();
      buildPlaylistUI();
      if (visibleTracks.length === 0) {
        setStatus("No playable songs right now");
        return;
      }
      if (wasCurrent) {
        trackIndex = pickRandomVisibleIndex(-1);
        loadTrack(trackIndex, soundUnlocked);
      } else if (trackIndex >= visibleTracks.length) {
        trackIndex = 0;
        updateTrackUI();
      }
    }

    function isEmbedError(code) {
      return code === 101 || code === 150 || code === 100;
    }

    function syncTrackMeta() {
      var track = getCurrentTrack();
      window.__portfolioMusic.trackIndex = trackIndex;
      window.__portfolioMusic.title = track.title;
      window.__portfolioMusic.artist = track.artist;
      window.__portfolioMusic.bpm = track.bpm || 118;
    }

    function updateTrackUI() {
      var track = getCurrentTrack();
      syncTrackMeta();
      if (titleEl) titleEl.textContent = track.title;
      if (artistEl) artistEl.textContent = track.artist;
      if (playlistEl) {
        var items = playlistEl.querySelectorAll(".music-playlist-item");
        for (var i = 0; i < items.length; i++) {
          items[i].classList.toggle("is-active", parseInt(items[i].getAttribute("data-index"), 10) === trackIndex);
        }
      }
      dispatch();
    }

    function buildPlaylistUI() {
      if (!playlistEl) return;
      playlistEl.innerHTML = "";
      if (visibleTracks.length === 0) {
        var empty = document.createElement("p");
        empty.className = "music-playlist-empty";
        empty.textContent = probeQueue.length > 0 ? "Finding playable songs…" : "No songs available";
        playlistEl.appendChild(empty);
        return;
      }
      for (var i = 0; i < visibleTracks.length; i++) {
        (function (idx) {
          var track = visibleTracks[idx];
          var btn = document.createElement("button");
          btn.type = "button";
          btn.className = "music-playlist-item" + (idx === trackIndex ? " is-active" : "");
          btn.setAttribute("data-index", String(idx));
          btn.innerHTML =
            '<span class="track-title">' + track.title + "</span>" +
            '<span class="track-artist">' + track.artist + "</span>";
          btn.addEventListener("click", function (e) {
            e.stopPropagation();
            selectTrack(idx, true);
          });
          playlistEl.appendChild(btn);
        })(i);
      }
    }

    function updateProbeStatus() {
      if (probeQueue.length === 0 && probeDone >= probeTotal) return;
      if (!statusEl || soundUnlocked) return;
      var ready = visibleTracks.length;
      setStatus("Checking songs… " + ready + " ready");
    }

    function buildDiscoverQueue() {
      probeQueue = [];
      for (var i = 0; i < MASTER_PLAYLIST.length; i++) {
        var id = MASTER_PLAYLIST[i].id;
        if (!verifiedIds.has(id) && !blockedIds.has(id)) probeQueue.push(id);
      }
      probeMode = "discover";
      probeTotal = probeQueue.length;
      probeDone = 0;
    }

    function buildRevalidateQueue() {
      probeQueue = visibleTracks.map(function (t) { return t.id; });
      probeMode = "revalidate";
      probeTotal = probeQueue.length;
      probeDone = 0;
    }

    function finishProbe(success) {
      if (probeTimer) {
        clearTimeout(probeTimer);
        probeTimer = null;
      }
      var id = probeCurrentId;
      probeCurrentId = null;
      probeDone += 1;
      if (id) {
        if (probeMode === "revalidate") {
          if (!success) blockTrack(id);
        } else if (success) {
          markTrackVerified(id);
        } else {
          blockedIds.add(id);
          persistBlocked();
        }
      }
      updateProbeStatus();
      probeNext();
    }

    function probeNext() {
      if (!probeReady) return;
      if (!probeQueue.length) {
        if (probeMode === "discover") {
          buildRevalidateQueue();
          if (probeQueue.length) {
            probeNext();
            return;
          }
        }
        if (!soundUnlocked && visibleTracks.length > 0) {
          setStatus("Tap anywhere for " + getCurrentTrack().title);
        }
        return;
      }
      probeCurrentId = probeQueue.shift();
      probeTimer = window.setTimeout(function () {
        probeCurrentId = null;
        probeDone += 1;
        probeNext();
      }, 8000);
      try {
        probePlayer.cueVideoById({ videoId: probeCurrentId, startSeconds: 0 });
      } catch (e) {
        finishProbe(false);
      }
    }

    function startProbePipeline() {
      if (!probeMountEl) return;
      buildDiscoverQueue();
      updateProbeStatus();
      probeNext();
    }

    function initProbePlayer(origin) {
      if (!probeMountEl || probePlayer) return;
      probePlayer = new window.YT.Player(probeMountEl, {
        height: "1",
        width: "1",
        videoId: SEED_VERIFIED[0],
        playerVars: {
          autoplay: 0,
          mute: 1,
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
            probeReady = true;
            startProbePipeline();
          },
          onStateChange: function (e) {
            if (!probeCurrentId) return;
            if (e.data === 5 || e.data === 1) finishProbe(true);
          },
          onError: function (e) {
            if (!probeCurrentId) return;
            if (isEmbedError(e.data)) finishProbe(false);
          },
        },
      });
    }

    rebuildVisibleTracks();
    trackIndex = pickRandomVisibleIndex(-1);
    updateTrackUI();
    buildPlaylistUI();
    setStatus("Loading player…");

    function validateLoadedTrack(shouldSkip) {
      if (!playerReady) return true;
      var track = getCurrentTrack();
      try {
        var data = player.getVideoData();
        var ytTitle = data && data.title;
        if (!ytTitle) return true;
        if (trackTitleMatches(track, ytTitle)) return true;
        setStatus("Wrong song — skipping…");
        blockTrack(track.id);
        if (shouldSkip && visibleTracks.length > 0) {
          trackIndex = pickRandomVisibleIndex(-1);
          loadTrack(trackIndex, soundUnlocked);
        }
        return false;
      } catch (e) {
        return true;
      }
    }

    function skipStartSec() {
      return getCurrentTrack().skip || 0;
    }

    function ensureVocalStart(force) {
      if (!playerReady) return;
      var skip = skipStartSec();
      if (skip <= 0) return;
      try {
        var cur = player.getCurrentTime();
        if (force || cur < skip - 0.35) {
          player.seekTo(skip, true);
        }
      } catch (e) { /* ignore */ }
    }

    function resetToMusicStart() {
      ensureVocalStart(true);
    }

    function danceBpm() {
      return getCurrentTrack().bpm || 118;
    }

    function setStatus(msg) {
      if (statusEl) statusEl.textContent = msg || "";
    }

    function togglePlaylist() {
      playlistOpen = !playlistOpen;
      if (playlistEl) playlistEl.classList.toggle("is-open", playlistOpen);
      if (playlistBtn) {
        playlistBtn.classList.toggle("is-open", playlistOpen);
        playlistBtn.setAttribute("aria-expanded", playlistOpen ? "true" : "false");
      }
      updateDancerBounds();
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
      if (playing) {
        if (!validateLoadedTrack(false)) return;
        ensureVocalStart(false);
        setStatus("");
      }
      dispatch();
    }

    function loadTrack(index, autoplay) {
      if (index < 0 || index >= visibleTracks.length) return;
      trackIndex = index;
      updateTrackUI();
      if (!playerReady) return;
      var track = getCurrentTrack();
      var wasPlaying = player.getPlayerState() === 1;
      try {
        if (autoplay || wasPlaying) {
          player.loadVideoById({
            videoId: track.id,
            startSeconds: skipStartSec(),
          });
          if (soundUnlocked) {
            player.unMute();
            player.setVolume(window.__portfolioMusic.volume);
            window.__portfolioMusic.muted = false;
            window.setTimeout(function () { ensureVocalStart(true); }, 180);
          }
        } else {
          player.cueVideoById({
            videoId: track.id,
            startSeconds: skipStartSec(),
          });
          prepareAtMusicStart();
        }
      } catch (e) {
        setStatus("Could not load track");
      }
      if (!soundUnlocked) setStatus("Tap anywhere for " + track.title);
    }

    function selectTrack(index, playNow) {
      if (index < 0 || index >= visibleTracks.length) return;
      if (index === trackIndex && playerReady) {
        if (playNow && player.getPlayerState() !== 1) {
          if (!soundUnlocked) beginAudiblePlayback();
          else {
            ensureVocalStart(false);
            player.playVideo();
          }
        }
        if (playlistOpen) togglePlaylist();
        return;
      }
      loadTrack(index, playNow && soundUnlocked);
      if (playNow && !soundUnlocked) beginAudiblePlayback();
      if (playlistOpen) togglePlaylist();
    }

    function playRandomNext() {
      if (visibleTracks.length <= 1) return;
      var next = pickRandomVisibleIndex(trackIndex);
      loadTrack(next, true);
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
          ensureVocalStart(false);
          player.playVideo();
          setVolumeIcon(window.__portfolioMusic.volume);
          dispatch();
        }
      }
    }

    loadYouTubeApi(function () {
      var origin = window.location.origin;
      if (!origin || origin === "null") origin = "http://localhost:3000";

      var initialTrack = getCurrentTrack();

      player = new window.YT.Player(mountEl, {
        height: "200",
        width: "200",
        videoId: initialTrack.id,
        playerVars: {
          autoplay: 0,
          mute: 1,
          start: initialTrack.skip || 0,
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
            if (!validateLoadedTrack(false)) return;
            setStatus("Tap anywhere for " + getCurrentTrack().title);
            if (pendingUnlock) beginAudiblePlayback();
          },
          onStateChange: function (e) {
            syncUiFromState(e.data);
            if (e.data === 1) markTrackVerified(getCurrentTrack().id);
            if (e.data === 0 && visibleTracks.length > 1) playRandomNext();
          },
          onError: function (e) {
            var code = e && e.data;
            if (isEmbedError(code)) {
              blockTrack(getCurrentTrack().id);
              if (visibleTracks.length > 0) {
                window.setTimeout(function () { playRandomNext(); }, 300);
              }
              return;
            }
            setStatus("Skipping…");
            window.setTimeout(function () { playRandomNext(); }, 400);
          },
        },
      });

      initProbePlayer(origin);
    });

    playBtn.addEventListener("click", function (e) {
      e.stopPropagation();
      if (!soundUnlocked) {
        beginAudiblePlayback();
        return;
      }
      togglePlay();
    });

    if (playlistBtn) {
      playlistBtn.addEventListener("click", function (e) {
        e.stopPropagation();
        togglePlaylist();
      });
    }

    document.addEventListener("click", function (e) {
      if (!playlistOpen) return;
      if (playlistEl && playlistEl.contains(e.target)) return;
      if (playlistBtn && playlistBtn.contains(e.target)) return;
      togglePlaylist();
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
        ensureVocalStart(false);
        player.playVideo();
      }
    });

    function musicBottomReserve() {
      return playlistOpen ? 340 : 112;
    }

    function zoneSize() {
      var rect = dancerZone ? dancerZone.getBoundingClientRect() : null;
      return {
        w: rect && rect.width > 0 ? rect.width : Math.min(window.innerWidth * 0.38, 360),
        h: rect && rect.height > 0 ? rect.height : window.innerHeight - 88,
      };
    }

    function updateDancerBounds() {
      if (!dancerZone) return;
      var reserve = musicBottomReserve();
      var clipBottom = reserve;
      var footer = document.querySelector(".site-footer");

      if (footer) {
        var top = footer.getBoundingClientRect().top;
        if (top < window.innerHeight - reserve) {
          clipBottom = Math.max(reserve, window.innerHeight - top + 16);
        }
      }

      dancerZone.style.bottom = clipBottom + "px";
      var height = dancerZone.getBoundingClientRect().height;
      dancerZone.classList.toggle("is-hidden", height < 100);
    }

    function dancerLayout(w, h) {
      var padX = 14;
      var padTop = 22;
      var padBottom = 22;
      var scaleW = (w - padX * 2) / CHAR_WIDTH;
      var scaleH = (h - padTop - padBottom) / (CHAR_FEET_Y + CHAR_TOP_EXTENT);
      var scale = Math.min(scaleW, scaleH, 2.45);
      return {
        scale: scale,
        footY: h - padBottom,
        padTop: padTop,
      };
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
      return dancerLayout(w, h).scale;
    }

    function drawBhangraDeveloper(now, w, h) {
      if (!dCtx) return;
      dCtx.clearRect(0, 0, w, h);

      var musicOn = window.__portfolioMusic.playing;
      var vol = musicOn ? window.__portfolioMusic.volume / 100 : 0;
      var layout = dancerLayout(w, h);

      if (musicOn) {
        var beatMs = 60000 / danceBpm() * 0.5;
        if (now - lastBeat > beatMs) {
          lastBeat = now;
          danceFrame = (danceFrame + 1) % BHANGRA_FRAMES;
          window.__portfolioMusic.beat = danceFrame;
          dispatch();
        }
      } else {
        danceFrame = 0;
        lastBeat = now;
      }

      var frameT = musicOn ? clamp((now - lastBeat) / (60000 / danceBpm() * 0.5), 0, 1) : 0;
      var poseA = getBhangraPose(musicOn ? danceFrame : 0);
      var poseB = getBhangraPose(musicOn ? danceFrame + 1 : 0);
      var pose = musicOn ? lerpPose(poseA, poseB, easeSnap(frameT)) : poseA;

      var scale = layout.scale;
      var baseX = w * 0.5 + (musicOn ? pose.lean * 8 * scale : 0);
      var footY = layout.footY;
      var bounce = musicOn ? Math.abs(Math.sin(now * 0.013)) * 18 * vol : 0;
      var shimmy = musicOn ? Math.sin(now * 0.026) * 7 : 0;
      var headBob = musicOn ? Math.sin(now * 0.039) * 4 : Math.sin(now * 0.002) * 1;

      dCtx.save();
      dCtx.translate(baseX + shimmy, footY - CHAR_FEET_Y * scale - bounce - (musicOn ? pose.hop * scale * 0.22 : 0));
      dCtx.scale(scale, scale);
      dCtx.globalAlpha = musicOn ? 0.92 : 0.78;

      dCtx.translate(0, musicOn ? pose.squat : 0);

      if (musicOn) {
        var glow = dCtx.createRadialGradient(0, 10, 8, 0, 20, 95);
        glow.addColorStop(0, "rgba(251,191,36,0.28)");
        glow.addColorStop(0.5, "rgba(52,211,153,0.16)");
        glow.addColorStop(1, "rgba(0,0,0,0)");
        dCtx.fillStyle = glow;
        dCtx.beginPath();
        dCtx.ellipse(0, 50, 78, 28, 0, 0, Math.PI * 2);
        dCtx.fill();
      }

      dCtx.save();
      dCtx.rotate(musicOn ? pose.tilt : 0);

      var hipY = 24, shoulderY = -12 + headBob;
      var armLen = musicOn ? 48 : 38;
      var armL = musicOn ? pose.armL : -Math.PI / 2 - 0.2;
      var armR = musicOn ? pose.armR : -Math.PI / 2 + 0.2;
      var legL = musicOn ? pose.legL : 0.05;
      var legR = musicOn ? pose.legR : -0.05;
      var lax = armPoint(-16, shoulderY, armL, armLen);
      var rax = armPoint(16, shoulderY, armR, armLen);
      var lkx = -18 + legL * 26, lky = hipY + 52 - Math.abs(legL) * 10;
      var rkx = 18 + legR * 26, rky = hipY + 52 - Math.abs(legR) * 10;

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
        dCtx.rotate(armR + 0.4);
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
      var musicOn = window.__portfolioMusic.playing;
      if (dancerZone) dancerZone.classList.toggle("is-dancing", musicOn);
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
