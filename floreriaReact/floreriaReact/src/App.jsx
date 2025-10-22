

import { Routes, Route } from 'react-router-dom';
import './App.css';
import { Home } from './pages/Home/Home';
import { Admin } from './pages/Admin/Admin';
import { Login } from './pages/Login/Login';
import { Contacto } from './pages/Contacto/Contacto';
import { Inventario } from './pages/Inventario/Inventario';
import { CrearProducto } from './componentes/CrearProd/CrearProducto';
import RutaProtegida from './componentes/RutaProtegida/RutaProtegida';
import CrearUsuario from './componentes/CrearUsuario/CrearUsuario';
import { EditarProd } from './componentes/EditarProd/EditarProd';


function App() {
  return (
    <Routes>
      {/* Rutas Públicas */}
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/contacto" element={<Contacto />} />

      {/* Rutas Protegidas para ADMIN */}
      <Route element={<RutaProtegida rolRequerido="ADMIN" />}>
        <Route path="/admin" element={<Admin />} />
        <Route path="/inventario" element={<Inventario />} />
        <Route path="/crear-producto" element={<CrearProducto />} />
        <Route path="/crear-usuario" element={<CrearUsuario />} />
        <Route path="/editar-producto/:id" element={<EditarProd />} />
      </Route>
    </Routes>
  );
}

export default App;


