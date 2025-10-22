
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

export function Navbar() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/'); // Redirige al home después del logout
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container">
                <Link className="navbar-brand" to="/">Florería</Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuNav">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="menuNav">
                    <ul className="navbar-nav ms-auto">
                        <li className="nav-item">
                            <Link className="nav-link active" to="/">Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/contacto">Contacto</Link>
                        </li>

                        {/* Enlaces solo para ADMIN */}
                        {user && user.rol === 'ADMIN' && (
                            <>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/inventario">Inventario</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/admin">Admin</Link>
                                </li>
                            </>
                        )}

                        {/* Lógica de Login/Logout */}
                        {user ? (
                            <li className="nav-item">
                                <button className="nav-link btn btn-link" onClick={handleLogout}>Logout</button>
                            </li>
                        ) : (
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">Login</Link>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
}