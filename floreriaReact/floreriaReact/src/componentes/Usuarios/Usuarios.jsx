
import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Usuarios.css';

const Usuarios = () => {
    const [usuarios, setUsuarios] = useState([]);

    const fetchUsuarios = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/usuarios');
            if (response.ok) {
                const data = await response.json();
                setUsuarios(data);
            } else {
                console.error('Error al obtener los usuarios');
            }
        } catch (error) {
            console.error('Error de red:', error);
        }
    };

    useEffect(() => {
        fetchUsuarios();
    }, []);

    const handleEliminar = async (id, nombre) => {
        if (window.confirm(`¿Estás seguro de que quieres ELIMINAR al usuario "${nombre}"? Esta acción es permanente.`)) {
            try {
                const response = await fetch(`http://localhost:8081/api/usuarios/${id}`, {
                    method: 'DELETE'
                });
                if (response.ok) {
                    alert('Usuario eliminado exitosamente');
                    fetchUsuarios();
                } else {
                    throw new Error('Error al eliminar el usuario');
                }
            } catch (error) {
                console.error('Error al eliminar:', error);
                alert('Error al eliminar el usuario');
            }
        }
    };

    return (
        <div className="container mi-tabla">
            <h3 style={{ marginBottom: '20px' }}>Gestión de Usuarios</h3>
            <div className="row mb-3">
                <div className="col-12 text-end">
                    <Link className="btn btn-outline-dark" style={{ fontSize: '13px' }} to="/crear-usuario">
                        Crear Usuario
                    </Link>
                </div>
            </div>

            <div className="row">
                <div className="col-md">
                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {usuarios.map(usuario => (
                                <tr key={usuario.id}>
                                    <td>{usuario.id}</td>
                                    <td>{usuario.nombre}</td>
                                    <td>{usuario.correo}</td>
                                    <td>{usuario.rol}</td>
                                    <td>
                                        <button 
                                            className="btn btn-sm btn-outline-danger"
                                            onClick={() => handleEliminar(usuario.id, usuario.nombre)}
                                        >
                                            Eliminar
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Usuarios;
