import { useEffect, useState } from 'react'
import { crearEnlace, listarEnlaces, obtenerTotales, urlCorta } from './api'
import Estadisticas from './components/Estadisticas'

export default function App() {
  const [url, setUrl] = useState('')
  const [alias, setAlias] = useState('')
  const [conAlias, setConAlias] = useState(false)
  const [nuevo, setNuevo] = useState(null)
  const [enlaces, setEnlaces] = useState([])
  const [totales, setTotales] = useState({})
  const [abierto, setAbierto] = useState(null)
  const [copiado, setCopiado] = useState('')
  const [error, setError] = useState('')
  const [enviando, setEnviando] = useState(false)

  // Cargar lista
  const cargar = async () => {
    try {
      const [lista, conteos] = await Promise.all([listarEnlaces(), obtenerTotales()])
      setEnlaces(lista)
      setTotales(Object.fromEntries(conteos.map((c) => [c.etiqueta, c.total])))
    } catch (e) {
      setError(e.message)
    }
  }

  useEffect(() => { cargar() }, [])

  // Acortar
  const acortar = async (e) => {
    e.preventDefault()
    setEnviando(true)
    setError('')
    try {
      const enlace = await crearEnlace(url.trim(), conAlias ? alias.trim() : null)
      setNuevo(enlace)
      setUrl('')
      setAlias('')
      cargar()
    } catch (err) {
      setError(err.message)
    }
    setEnviando(false)
  }

  // Copiar
  const copiar = async (codigo) => {
    await navigator.clipboard.writeText(urlCorta(codigo))
    setCopiado(codigo)
    setTimeout(() => setCopiado(''), 1500)
  }

  return (
    <div className="pagina">
      <header className="cabecera">
        <span className="logo"><span className="logo-icono">↗</span><span>Mi<b>Link</b></span></span>
        <span className="lema">Altoque nomás</span>
      </header>

      {/* Formulario */}
      <section className="hero">
        <h1>Enlaces cortos,<br /><span>estadísticas claras.</span></h1>
        <p>Pega un link largo, compártelo corto y mira cuántas personas lo abren.</p>

        <form onSubmit={acortar} className="caja">
          <div className="caja-fila">
            <input
              type="url"
              placeholder="https://un-enlace-muy-largo.com/..."
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              required
            />
            <button disabled={enviando}>{enviando ? '...' : 'Acortar'}</button>
          </div>

          <label className="check">
            <input type="checkbox" checked={conAlias} onChange={(e) => setConAlias(e.target.checked)} />
            Usar un alias personalizado
          </label>

          {conAlias && (
            <div className="alias">
              <span>/r/</span>
              <input placeholder="mi-portafolio" value={alias} onChange={(e) => setAlias(e.target.value)} />
            </div>
          )}

          {error && <p className="error">{error}</p>}
        </form>

        {/* Resultado */}
        {nuevo && (
          <div className="resultado">
            <div>
              <small>Tu enlace corto</small>
              <a href={urlCorta(nuevo.codigo)} target="_blank" rel="noreferrer">{urlCorta(nuevo.codigo)}</a>
            </div>
            <button onClick={() => copiar(nuevo.codigo)}>{copiado === nuevo.codigo ? '¡Copiado!' : 'Copiar'}</button>
          </div>
        )}
      </section>

      {/* Lista */}
      <section className="lista">
        <h2>Tus enlaces <span>{enlaces.length}</span></h2>

        {enlaces.length === 0 && <p className="tenue centro">Todavía no creaste ningún enlace.</p>}

        {enlaces.map((e) => (
          <article key={e.codigo} className={`fila ${abierto === e.codigo ? 'fila-abierta' : ''}`}>
            <div className="fila-principal">
              <div className="fila-textos">
                <a className="corto" href={urlCorta(e.codigo)} target="_blank" rel="noreferrer">/r/{e.codigo}</a>
                <span className="original" title={e.urlOriginal}>{e.urlOriginal}</span>
              </div>
              <div className="clics">
                <strong>{totales[e.codigo] || 0}</strong>
                <small>clics</small>
              </div>
              <div className="acciones">
                <button className="boton-chico" onClick={() => copiar(e.codigo)}>
                  {copiado === e.codigo ? '✓' : 'Copiar'}
                </button>
                <button className="boton-chico" onClick={() => setAbierto(abierto === e.codigo ? null : e.codigo)}>
                  {abierto === e.codigo ? 'Cerrar' : 'Estadísticas'}
                </button>
              </div>
            </div>
            {abierto === e.codigo && <Estadisticas codigo={e.codigo} />}
          </article>
        ))}
      </section>

      <footer className="pie">MiLink · Hecho por Ever Montero</footer>
    </div>
  )
}
