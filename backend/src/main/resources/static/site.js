window.renderMarkdownFromTextarea = function renderMarkdownFromTextarea(sourceId, targetId) {
  const source = document.getElementById(sourceId);
  const target = document.getElementById(targetId);

  if (!source || !target) {
    return;
  }

  const markdown = source.value || "";

  if (window.marked) {
    target.innerHTML = window.marked.parse(markdown);
    return;
  }

  target.textContent = markdown;
};

(function initializeBackgroundShift() {
  if (window.__portfolioBackgroundShiftInitialized) {
    return;
  }

  window.__portfolioBackgroundShiftInitialized = true;

  const reducedMotion = window.matchMedia && window.matchMedia('(prefers-reduced-motion: reduce)').matches;
  if (reducedMotion) {
    return;
  }

  let ticking = false;
  let currentShift = 0;

  const updateBackgroundShift = () => {
    const targetShift = Math.min(window.scrollY * 0.035, 26);
    currentShift += (targetShift - currentShift) * 0.09;
    document.documentElement.style.setProperty('--bg-shift-y', `${currentShift.toFixed(2)}px`);

    if (Math.abs(targetShift - currentShift) > 0.08) {
      window.requestAnimationFrame(updateBackgroundShift);
    } else {
      currentShift = targetShift;
      document.documentElement.style.setProperty('--bg-shift-y', `${currentShift.toFixed(2)}px`);
      ticking = false;
    }
  };

  updateBackgroundShift();

  window.addEventListener('scroll', () => {
    if (!ticking) {
      ticking = true;
      window.requestAnimationFrame(updateBackgroundShift);
    }
  }, { passive: true });
})();
