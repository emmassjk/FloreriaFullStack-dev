import { useEffect, useState } from 'react';
import './Home.css';
import { Navbar } from '../../componentes/Navbar/Navbar';

const API_BASE_URL = 'http://localhost:8081';

export function Home() {
    const [productos, setProductos] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [searchTerm, setSearchTerm] = useState(''); // Estado para la búsqueda
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const cargarProductos = async () => {
            try {
                setLoading(true);
                const response = await fetch(`${API_BASE_URL}/api/productos`);
                if (!response.ok) throw new Error('Error al cargar productos');
                const data = await response.json();
                // Filtra solo los productos activos.
                setProductos(data.filter(p => p.activo === true || p.activo === 'true'));
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        cargarProductos();
    }, []);

    const renderStock = (stock) => {
        if (stock <= 0) {
            return <span className="badge bg-danger">Agotado</span>;
        }
        if (stock < 3) {
            return <span style={{ color: 'red', fontWeight: '500' }}>¡Quedan solo {stock}!</span>;
        }
        return <span>Stock: {stock}</span>;
    };

    if (loading) return <div className="container mt-5 text-center"><p>Cargando...</p></div>;
    if (error) return <div className="container mt-5 alert alert-danger">{error}</div>;

    // Lógica de filtrado: se aplica a la lista completa de productos.
    const filteredProductos = productos.filter(producto =>
        producto.nombre.toLowerCase().includes(searchTerm.toLowerCase())
    );
    
    // Función para manejar el cambio en la barra de búsqueda
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    // VISTA DE DETALLE (sin cambios)
    if (selectedProduct) {
        return (
            <>
                <Navbar />
                <div className="container mt-4">
                    <div className="row">
                        <div className="col-md-6">
                            <img
                                src={selectedProduct.imagen ? selectedProduct.imagen.replace('/uploads/', '/upload/') : 'https://via.placeholder.com/600x400'}
                                className="img-fluid rounded"
                                alt={selectedProduct.nombre}
                            />
                        </div>
                        <div className="col-md-6">
                            <h2>{selectedProduct.nombre}</h2>
                            <p className="lead">{selectedProduct.descripcion}</p>
                            <h3 className="my-3">Precio: ${selectedProduct.precio}</h3>
                            <div className="d-flex align-items-center mb-3">
                                <span className="me-2">Disponibilidad:</span>
                                {renderStock(selectedProduct.stock)}
                            </div>
                            <button className="btn btn-outline-dark" onClick={() => setSelectedProduct(null)}>
                                ← Volver a la tienda
                            </button>
                        </div>
                    </div>
                </div>
            </>
        );
    }

    // VISTA DE LISTA (modificada con barra de búsqueda)
    return (
        <>
            <Navbar />
            <div className="container">
                <div className="text-center my-4">
                    <h1>¡Bienvenido a la Floreria Julia Rosa!</h1>
                    <p>¡Mira nuestra hermosa selección de flores y arreglos!</p>
                </div>
                
                {/* --- Barra de Búsqueda --- */}
                <div className="row mb-4">
                    <div className="col-12">
                        <div className="input-group">
                            <span className="input-group-text"></span>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Buscar productos por nombre..."
                                value={searchTerm}
                                onChange={handleSearchChange} // Actualiza `searchTerm` con cada tecla
                            />
                        </div>
                    </div>
                </div>
                {/* ------------------------- */}
                
                <div className="row">
                    {/* Renderiza la lista filtrada */}
                    {filteredProductos.length > 0 ? (
                        filteredProductos.map(producto => (
                            <div key={producto.id} className="col-md-4 mb-4">
                                <div className="card h-100 product-card">
                                    <img
                                        src={producto.imagen ? producto.imagen.replace('/uploads/', '/upload/') : 'https://via.placeholder.com/300x200'}
                                        className="card-img-top"
                                        alt={producto.nombre}
                                    />
                                    <div className="card-body d-flex flex-column">
                                        <h5 className="card-title">{producto.nombre}</h5>
                                        <div className="d-flex justify-content-between align-items-center mb-2">
                                            <p className="card-text mb-0"><b>${producto.precio}</b></p>
                                            {renderStock(producto.stock)}
                                        </div>
                                        <button onClick={() => setSelectedProduct(producto)} className="btn btn-primary mt-auto">
                                            Ver Detalles
                                        </button>
                                    </div>
                                </div>
                            </div>
                        ))
                    ) : (
                        // Mensaje cuando no hay resultados
                        <div className="col-12 text-center mt-5">
                            <p className="lead">No se encontraron productos que coincidan con "{searchTerm}".</p>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}