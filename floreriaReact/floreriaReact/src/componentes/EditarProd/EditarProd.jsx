
import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Navbar } from '../Navbar/Navbar';

export function EditarProd() {
    const { id } = useParams();
    const navigate = useNavigate();
    const productoId = parseInt(id);

    const [producto, setProducto] = useState({
        nombre: '',
        descripcion: '',
        precio: '',
        categoria: { id: '', nombre: '' }
    });

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState(null);
    const [success, setSuccess] = useState(false);

    useEffect(() => {
        const cargarDatos = async () => {
            try {
                setLoading(true);
                const respProducto = await fetch(`http://localhost:8081/api/productos/${productoId}`);
                if (!respProducto.ok) throw new Error('Error al cargar el producto');
                const dataProducto = await respProducto.json();
                setProducto(dataProducto);
                setError(null);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        if (productoId) cargarDatos();
    }, [productoId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProducto(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async () => {
        setSaving(true);
        setError(null);
        setSuccess(false);

        try {
            const datosActualizados = { ...producto };
            const response = await fetch(`http://localhost:8081/api/productos/${productoId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datosActualizados)
            });

            if (!response.ok) throw new Error('Error al actualizar el producto');
            
            await response.json();
            setSuccess(true);

            setTimeout(() => navigate('/inventario'), 1500);

        } catch (err) {
            setError(err.message);
        } finally {
            setSaving(false);
        }
    };

    const handleVolver = () => navigate('/inventario');

    if (loading) return <div className="container mt-5 text-center"><p>Cargando producto...</p></div>;

    return (
        <>
            <Navbar />
            <div className="container mt-3" style={{ maxWidth: '600px' }}>
                <div className="card">
                    <div className="card-header bg-primary text-white d-flex justify-content-between align-items-center py-2">
                        <h6 className="mb-0">Editar Producto</h6>
                        <button onClick={handleVolver} type="button" className="btn-close btn-close-white" aria-label="Cerrar"></button>
                    </div>
                    <div className="card-body p-3">
                        {error && <div className="alert alert-danger py-2 mb-2 small">{error}</div>}
                        {success && <div className="alert alert-success py-2 mb-2 small">Producto actualizado exitosamente</div>}
                        
                        <div className="mb-2">
                            <label className="form-label small mb-1">Nombre</label>
                            <input type="text" name="nombre" value={producto.nombre} onChange={handleChange} className="form-control form-control-sm" />
                        </div>
                        <div className="mb-2">
                            <label className="form-label small mb-1">Descripción</label>
                            <textarea name="descripcion" value={producto.descripcion} onChange={handleChange} rows={2} className="form-control form-control-sm" />
                        </div>
                        <div className="mb-2">
                            <label className="form-label small mb-1">Precio</label>
                            <input type="number" name="precio" value={producto.precio} onChange={handleChange} className="form-control form-control-sm" />
                        </div>
                        <div className="mb-2">
                            <label className="form-label small mb-1">Stock</label>
                            <input type="number" name="stock" value={producto.stock} onChange={handleChange} className="form-control form-control-sm" />
                        </div>

                        <div className="d-flex gap-2 pt-2 border-top">
                            <button onClick={handleVolver} type="button" className="btn btn-sm btn-secondary">Cancelar</button>
                            <button onClick={handleSubmit} disabled={saving} type="button" className="btn btn-sm btn-primary flex-grow-1">
                                {saving ? 'Guardando...' : 'Guardar Cambios'}
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
