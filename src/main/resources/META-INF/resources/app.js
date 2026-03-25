document.getElementById("calc-form").addEventListener("submit", async function(e) {
    e.preventDefault();
    
    const input = document.getElementById("n-input");
    const n = input.value;
    
    if (n === "" || isNaN(n) || n < 0) {
        showError("Please enter a valid non-negative integer");
        return;
    }

    setLoading(true);
    hideError();
    hideResult();

    const start = performance.now();
    try {
        const response = await fetch(`/labseq/${n}`);
        
        if (!response.ok) {
            throw new Error(`Server returned ${response.status}`);
        }
        
        const data = await response.text();
        const end = performance.now();
        
        showResult(data, (end - start).toFixed(2));
    } catch (err) {
        showError("Failed to fetch calculation. Is the server running?");
        console.error(err);
    } finally {
        setLoading(false);
    }
});

function setLoading(isLoading) {
    const btnText = document.querySelector(".btn-text");
    const spinner = document.getElementById("loading-spinner");
    const btn = document.getElementById("submit-btn");

    if (isLoading) {
        btnText.textContent = "Calculating...";
        spinner.classList.remove("disable");
        btn.disabled = true;
    } else {
        btnText.textContent = "Calculate";
        spinner.classList.add("disable");
        btn.disabled = false;
    }
}

function showResult(value, timeMs) {
    document.getElementById("result-value").textContent = value;
    document.getElementById("time-taken").textContent = timeMs;
    document.getElementById("result-box").classList.remove("hidden");
}

function hideResult() {
    document.getElementById("result-box").classList.add("hidden");
}

function showError(msg) {
    document.getElementById("error-msg").textContent = msg;
    document.getElementById("error-box").classList.remove("hidden");
}

function hideError() {
    document.getElementById("error-box").classList.add("hidden");
}
