const $ = (id) => document.getElementById(id);

function formatDateTime(iso) {
    if (!iso) return "—";
    const d = new Date(iso);
    if (Number.isNaN(d.getTime())) return String(iso);
    return d.toLocaleString("es-CO", {
        dateStyle: "short",
        timeStyle: "short"
    });
}

async function parseError(res) {
    try {
        const j = await res.json();
        if (j && j.error) return j.error;
    } catch (_) { /* ignore */ }
    return "No se pudo completar la solicitud (código " + res.status + ").";
}

async function fetchJson(url, options) {
    const res = await fetch(url, options);
    if (!res.ok) {
        const msg = await parseError(res);
        throw new Error(msg);
    }
    return res.json();
}

function setResult(el, text, variant) {
    el.textContent = text;
    el.classList.remove("error", "ok");
    if (variant === "error") el.classList.add("error");
    if (variant === "ok") el.classList.add("ok");
}

async function cargarEjemploItinerario() {
    const out = $("out-itinerario");
    try {
        const data = await fetchJson("/api/vuelos/AV123");
        const lines = [
            "Ejemplo cargado desde el servidor (vuelo AV123):",
            "Destino: " + data.destino,
            "Hora de salida: " + formatDateTime(data.horaSalida),
            "Puerta de embarque: " + data.puertaEmbarque,
            "Estado del vuelo: " + data.estado
        ];
        setResult(out, lines.join("\n"), "ok");
        $("input-itinerario").value = "AV123";
    } catch (e) {
        setResult(out, "No se pudo cargar el ejemplo (¿PostgreSQL activo?).\n" + e.message, "error");
    }
}

async function consultarItinerario() {
    const num = $("input-itinerario").value.trim();
    const out = $("out-itinerario");
    if (!num) {
        setResult(out, "Ingrese un número de vuelo.", "error");
        return;
    }
    try {
        const data = await fetchJson("/api/vuelos/" + encodeURIComponent(num));
        const lines = [
            "Destino: " + data.destino,
            "Hora de salida: " + formatDateTime(data.horaSalida),
            "Puerta de embarque: " + data.puertaEmbarque,
            "Estado del vuelo: " + data.estado
        ];
        setResult(out, lines.join("\n"), "ok");
    } catch (e) {
        setResult(out, e.message, "error");
    }
}

async function consultarNotificacion() {
    const num = $("input-notif").value.trim();
    const out = $("out-notif");
    if (!num) {
        setResult(out, "Ingrese un número de vuelo.", "error");
        return;
    }
    try {
        const data = await fetchJson("/api/vuelos/" + encodeURIComponent(num) + "/notificacion");
        setResult(out, data.mensaje, "ok");
    } catch (e) {
        setResult(out, e.message, "error");
    }
}

async function consultarPuerta() {
    const num = $("input-puerta").value.trim();
    const out = $("out-puerta");
    if (!num) {
        setResult(out, "Ingrese un número de vuelo.", "error");
        return;
    }
    try {
        const data = await fetchJson("/api/vuelos/" + encodeURIComponent(num) + "/puerta");
        setResult(out, data.mensaje, "ok");
    } catch (e) {
        setResult(out, e.message, "error");
    }
}

async function enviarReporte() {
    const numeroVuelo = $("input-rep-vuelo").value.trim();
    const numeroEquipaje = $("input-rep-equipaje").value.trim();
    const descripcion = $("input-rep-desc").value.trim();
    const out = $("out-reporte");
    if (!numeroVuelo || !numeroEquipaje || !descripcion) {
        setResult(out, "Complete todos los campos del reporte.", "error");
        return;
    }
    try {
        const data = await fetchJson("/api/reportes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ numeroVuelo, numeroEquipaje, descripcion })
        });
        setResult(out, "✅ Reporte #" + data.numeroReporte + " registrado con éxito", "ok");
    } catch (e) {
        setResult(out, e.message, "error");
    }
}

document.addEventListener("DOMContentLoaded", () => {
    $("btn-itinerario").addEventListener("click", consultarItinerario);
    $("btn-notif").addEventListener("click", consultarNotificacion);
    $("btn-puerta").addEventListener("click", consultarPuerta);
    $("btn-reporte").addEventListener("click", enviarReporte);

    $("input-itinerario").addEventListener("keydown", (ev) => {
        if (ev.key === "Enter") consultarItinerario();
    });
    $("input-notif").addEventListener("keydown", (ev) => {
        if (ev.key === "Enter") consultarNotificacion();
    });
    $("input-puerta").addEventListener("keydown", (ev) => {
        if (ev.key === "Enter") consultarPuerta();
    });

    cargarEjemploItinerario();
});
