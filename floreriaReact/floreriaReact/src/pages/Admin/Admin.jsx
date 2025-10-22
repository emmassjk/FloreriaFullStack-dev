

import { Navbar } from '../../componentes/Navbar/Navbar';
import Usuarios from '../../componentes/Usuarios/Usuarios';
import './Admin.css';

export function Admin() {
    return (
        <>
            <Navbar />
            <div className="container mt-4">
                <Usuarios />
            </div>
        </>
    );
}