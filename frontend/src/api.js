// Cliente HTTP
async function pedir(ruta, opciones = {}) {
  const respuesta = await fetch(ruta, {
    headers: { 'Content-Type': 'application/json' },
    ...opciones
  })

  if (!respuesta.ok) {
    const error = await respuesta.json().catch(() => ({}))
    throw new Error(error.mensaje || 'Ocurrió un error, intenta de nuevo')
  }

  return respuesta.json()
}

// Enlaces
export const crearEnlace = (urlOriginal, alias) =>
  pedir('/api/enlaces', {
    method: 'POST',
    body: JSON.stringify({ urlOriginal, alias: alias || null })
  })

export const listarEnlaces = () => pedir('/api/enlaces')

// Estadísticas
export const obtenerTotales = () => pedir('/api/estadisticas/totales')

export const obtenerEstadisticas = (codigo) => pedir(`/api/estadisticas/${codigo}`)

// Link corto completo
export const urlCorta = (codigo) => `${window.location.origin}/r/${codigo}`
