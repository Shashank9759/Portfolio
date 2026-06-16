(function () {
  "use strict";

  var THEME_KEY = "portfolio-theme";
  var themes = [
    { id: "midnight", label: "Midnight", color: "#3b82f6" },
    { id: "light", label: "Light", color: "#2563eb" },
    { id: "android", label: "Android", color: "#3ddc84" },
    { id: "ocean", label: "Ocean", color: "#06b6d4" },
    { id: "sunset", label: "Sunset", color: "#f97316" },
    { id: "cyber", label: "Cyber", color: "#00ff88" },
    { id: "amoled", label: "AMOLED", color: "#bb86fc" },
    { id: "forest", label: "Forest", color: "#22c55e" },
    { id: "rose", label: "Rose", color: "#f43f5e" },
    { id: "lavender", label: "Lavender", color: "#a78bfa" },
    { id: "gold", label: "Gold", color: "#eab308" },
    { id: "crimson", label: "Crimson", color: "#dc2626" },
    { id: "mint", label: "Mint", color: "#2dd4bf" },
    { id: "slate", label: "Slate", color: "#94a3b8" },
    { id: "neon", label: "Neon", color: "#ff2d95" },
    { id: "arctic", label: "Arctic", color: "#38bdf8" },
    { id: "ember", label: "Ember", color: "#fb7185" },
  ];

  function $(sel, root) {
    return (root || document).querySelector(sel);
  }

  function $$(sel, root) {
    return Array.prototype.slice.call((root || document).querySelectorAll(sel));
  }

  function easeOutCubic(t) {
    return 1 - Math.pow(1 - t, 3);
  }

  function smoothScrollTo(targetY, duration) {
    var start = window.scrollY;
    var distance = targetY - start;
    var startTime = null;
    function step(ts) {
      if (!startTime) startTime = ts;
      var p = Math.min(1, (ts - startTime) / duration);
      window.scrollTo(0, start + distance * easeOutCubic(p));
      if (p < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  }

  function initTheme() {
    var saved = localStorage.getItem(THEME_KEY) || "midnight";
    document.documentElement.setAttribute("data-theme", saved);

    var chip = $("#theme-chip");
    var menu = $("#theme-menu");
    if (!chip || !menu) return;

    themes.forEach(function (t) {
      var btn = document.createElement("button");
      btn.type = "button";
      btn.className = "theme-option" + (t.id === saved ? " active" : "");
      btn.innerHTML = '<span class="theme-dot" style="background:' + t.color + '"></span>' + t.label;
      btn.addEventListener("click", function () {
        document.documentElement.setAttribute("data-theme", t.id);
        localStorage.setItem(THEME_KEY, t.id);
        $$(".theme-option", menu).forEach(function (o) { o.classList.remove("active"); });
        btn.classList.add("active");
        var label = $("#theme-label");
        if (label) label.textContent = t.label;
        menu.classList.remove("open");
        window.dispatchEvent(new Event("themechange"));
      });
      menu.appendChild(btn);
    });

    var current = themes.find(function (t) { return t.id === saved; }) || themes[0];
    var label = $("#theme-label");
    if (label) label.textContent = current.label;

    chip.addEventListener("click", function (e) {
      e.stopPropagation();
      menu.classList.toggle("open");
    });
    document.addEventListener("click", function () { menu.classList.remove("open"); });
  }

  function initNav() {
    var header = $(".site-header");
    var mobileNav = $(".mobile-nav");
    var toggle = $(".menu-toggle");

    if (toggle && mobileNav) {
      toggle.addEventListener("click", function () { mobileNav.classList.toggle("open"); });
    }

    $$(".nav-link, .fab, .hero-actions a[href^='#']").forEach(function (link) {
      link.addEventListener("click", function (e) {
        var href = link.getAttribute("href");
        if (!href || href.charAt(0) !== "#") return;
        var el = document.querySelector(href);
        if (!el) return;
        e.preventDefault();
        var top = el.getBoundingClientRect().top + window.scrollY - 80;
        smoothScrollTo(top, 420);
        if (mobileNav) mobileNav.classList.remove("open");
      });
    });

    window.addEventListener("scroll", function () {
      if (!header) return;
      header.classList.toggle("scrolled", window.scrollY > 24);
    }, { passive: true });
  }

  function initReveal() {
    var items = $$(".reveal");
    if (!items.length || !("IntersectionObserver" in window)) {
      items.forEach(function (el) { el.classList.add("visible"); });
      return;
    }
    var io = new IntersectionObserver(function (entries) {
      entries.forEach(function (entry) {
        if (entry.isIntersecting) {
          entry.target.classList.add("visible");
          io.unobserve(entry.target);
        }
      });
    }, { threshold: 0.12, rootMargin: "0px 0px -40px 0px" });
    items.forEach(function (el) { io.observe(el); });
  }

  function animateCounter(el, target, suffix, duration) {
    var start = null;
    function step(ts) {
      if (!start) start = ts;
      var p = Math.min(1, (ts - start) / duration);
      var val = Math.round(target * easeOutCubic(p));
      el.textContent = val + (suffix || "");
      if (p < 1) requestAnimationFrame(step);
    }
    requestAnimationFrame(step);
  }

  function initCounters() {
    $$(".stat-value[data-target]").forEach(function (el) {
      var io = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
          if (!entry.isIntersecting) return;
          var target = parseInt(el.getAttribute("data-target"), 10);
          var suffix = el.getAttribute("data-suffix") || "";
          var star = el.getAttribute("data-star") === "true";
          animateCounter(el, target, suffix, 1500);
          if (star) {
            var s = document.createElement("span");
            s.className = "star";
            s.textContent = "★";
            el.appendChild(s);
          }
          io.disconnect();
        });
      }, { threshold: 0.5 });
      io.observe(el);
    });
  }

  function initSkillBars() {
    $$(".skill-fill").forEach(function (bar) {
      var io = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
          if (entry.isIntersecting) {
            bar.classList.add("animated");
            io.disconnect();
          }
        });
      }, { threshold: 0.3 });
      io.observe(bar);
    });
  }

  function initServices() {
    var chips = $$(".service-chip");
    var panels = $$(".service-panel");
    var gridCards = $$(".service-grid-card");
    if (!chips.length) return;

    var userPicked = false;
    var autoIndex = 0;

    function select(index) {
      chips.forEach(function (c, i) { c.classList.toggle("active", i === index); });
      panels.forEach(function (p) {
        p.classList.toggle("active", parseInt(p.getAttribute("data-service-panel"), 10) === index);
      });
      gridCards.forEach(function (c, i) { c.classList.toggle("selected", i === index); });
      autoIndex = index;
    }

    chips.forEach(function (chip, i) {
      chip.addEventListener("click", function () {
        userPicked = true;
        select(i);
      });
    });

    gridCards.forEach(function (card, i) {
      card.addEventListener("click", function () {
        userPicked = true;
        select(i);
      });
    });

    var section = $("#services");
    var timer = null;
    function startAuto() {
      if (timer) clearInterval(timer);
      if (window.innerWidth < 1024) return;
      timer = setInterval(function () {
        if (userPicked) return;
        if (section) {
          var r = section.getBoundingClientRect();
          if (r.bottom < 0 || r.top > window.innerHeight) return;
        }
        autoIndex = (autoIndex + 1) % chips.length;
        select(autoIndex);
      }, 5000);
    }
    startAuto();
    window.addEventListener("resize", startAuto);
  }

  function buildGmailComposeUrl(to, subject, body) {
    return (
      "https://mail.google.com/mail/?view=cm&fs=1" +
      "&to=" + encodeURIComponent(to) +
      "&su=" + encodeURIComponent(subject) +
      "&body=" + encodeURIComponent(body)
    );
  }

  function initContact() {
    var form = $("#contact-form");
    var err = $("#form-error");
    if (!form) return;
    form.addEventListener("submit", function (e) {
      e.preventDefault();
      var fd = new FormData(form);
      var name = String(fd.get("name") || "").trim();
      var subject = String(fd.get("subject") || "").trim();
      var message = String(fd.get("message") || "").trim();
      if (!name || !subject || !message) {
        if (err) { err.textContent = "Please fill in all fields."; err.classList.remove("hidden"); }
        return;
      }
      var email = form.getAttribute("data-email") || "shashankranjantech@gmail.com";
      var emailSubject = subject + " - " + name;
      var gmailUrl = buildGmailComposeUrl(email, emailSubject, message);
      window.open(gmailUrl, "_blank", "noopener,noreferrer");
      if (err) err.classList.add("hidden");
      form.reset();
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    initTheme();
    initNav();
    initReveal();
    initCounters();
    initSkillBars();
    initServices();
    initContact();
  });
})();
