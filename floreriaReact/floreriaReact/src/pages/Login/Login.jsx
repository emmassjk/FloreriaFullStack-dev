
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import { Navbar } from '../../componentes/Navbar/Navbar';
import './Login.css'; // Asegúrate de crear este archivo para los estilos

export function Login() {
    const [correo, setCorreo] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(''); // Limpiar errores previos
        try {
            await login(correo, password);
            navigate('/admin'); // Redirigir a la página de admin en caso de éxito
        } catch (err) {
            setError('Credenciales incorrectas. Por favor, inténtalo de nuevo.');
        }
    };

    return (
        <>
            <Navbar />
            <div className="login-container">
                <form className="login-form" onSubmit={handleSubmit}>
                    <h2>Iniciar Sesión</h2>
                    {error && <p className="error-message">{error}</p>}
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
                    <button type="submit" className="login-button">Entrar</button>
                </form>
            </div>
        </>
    );
}