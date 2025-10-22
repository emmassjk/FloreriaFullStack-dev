
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar } from '../Navbar/Navbar';
import './CrearUsuario.css';

const CrearUsuario = () => {
    const [nombre, setNombre] = useState('');
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [rol, setRol] = useState('USER');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const nuevoUsuario = { nombre, correo, password, rol };

        try {
            const response = await fetch('http://localhost:8081/api/usuarios', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(nuevoUsuario),
            });

            if (response.ok) {
                navigate('/admin'); // Redirigir a la página de admin
            } else {
                console.error('Error al crear el usuario');
            }
        } catch (error) {
            console.error('Error de red:', error);
        }
    };

    return (
        <>
            <Navbar />
            <div className="crear-usuario-container">
                <h2>Crear Nuevo Usuario</h2>
                <form onSubmit={handleSubmit} className="crear-usuario-form">
                    <div className="form-group">
                        <label htmlFor="nombre">Nombre</label>
                        <input
                            type="text"
                            id="nombre"
                            value={nombre}
                            onChange={(e) => setNombre(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="correo">Correo Electrónico</label>
                        <input
                            type="email"
                            id="correo"
                            value={correo}
                            onChange={(e) => setCorreo(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="password">Contraseña</label>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="rol">Rol</label>
                        <select id="rol" value={rol} onChange={(e) => setRol(e.target.value)}>
                            <option value="USER">User</option>
                            <option value="ADMIN">Admin</option>
                        </select>
                    </div>
                    <button type="submit" className="btn btn-primary">Crear Usuario</button>
                </form>
            </div>
        </>
    );
};

export default CrearUsuario;
