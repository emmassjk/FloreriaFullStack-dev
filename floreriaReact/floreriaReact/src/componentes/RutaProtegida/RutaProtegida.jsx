
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const RutaProtegida = ({ rolRequerido }) => {
    const { user, loading } = useAuth();

    if (loading) {
        // Muestra un spinner o un mensaje de carga mientras se verifica la autenticación
        return <div>Cargando...</div>;
    }

    if (!user) {
        // Si no hay usuario, redirige a la página de login
        return <Navigate to="/login" />;
    }

    if (rolRequerido && user.rol !== rolRequerido) {
        // Si se requiere un rol y el usuario no lo tiene, redirige al home
        return <Navigate to="/" />;
    }

    // Si el usuario está autenticado y tiene el rol correcto (o no se requiere rol), muestra el contenido
    return <Outlet />;
};

export default RutaProtegida;
