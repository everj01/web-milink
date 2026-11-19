import { useEffect, useState } from 'react'
import { obtenerEstadisticas } from '../api'

const nombreDia = (fecha) =>
  new Date(fecha + 'T00:00:00').toLocaleDateString('es-PE', { weekday: 'short' }).replace('.', '')

export default function Estadisticas({ codigo }) {
  const [datos, setDatos] = useState(null)

  // Cargar estadísticas
  useEffect(() => {
    obtenerEstadisticas(codigo).then(setDatos).catch(() => setDatos(null))
  }, [codigo])

  if (!datos) return <div className="stats"><p className="tenue">Cargando...</p></div>

  const maxDia = Math.max(1, ...datos.porDia.map((d) => d.total))

  return (
    <div className="stats">
      {/* Clics por día */}
      <div>
        <h4>Últimos 7 días</h4>
        <div className="barras">
          {datos.porDia.map((d) => (
            <div key={d.etiqueta} className="barra-col">
              <span className="barra-valor">{d.total}</span>
              <div className="barra" style={{ height: `${(d.total / maxDia) * 100}%` }} />
              <span className="barra-dia">{nombreDia(d.etiqueta)}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Navegadores */}
      <div>
        <h4>Navegadores</h4>
        {datos.porNavegador.length === 0 && <p className="tenue">Aún no hay clics.</p>}
        {datos.porNavegador.map((n) => (
          <div key={n.etiqueta} className="nav-fila">
            <span>{n.etiqueta}</span>
            <div className="nav-pista">
              <div className="nav-relleno" style={{ width: `${(n.total / datos.total) * 100}%` }} />
            </div>
            <strong>{n.total}</strong>
          </div>
        ))}
      </div>
    </div>
  )
}
