import { Navbar } from '../../componentes/Navbar/Navbar';
import './Contacto.css'; 
import logo from '../../assets/img/logo.png'; 
export function Contacto() {
    const whatsappNumber = '+56912345678'; 

    return (
        <>
            <Navbar />
            <div className="container">
                <h1>Contáctanos</h1>
                <img src={logo} alt="Floreria Julia Rosa Logo" className="logo" />
                <p>¡Estamos aquí para ayudarte! Contáctanos a través de los siguientes medios:</p>

                <div className="contact-options">
                    <a href="https://www.instagram.com/floreria.juliarosa/"
                        className="btn instagram-button"
                        target="_blank"
                        rel="noopener noreferrer">
                        Visítanos en Instagram
                    </a>
                    <a href={`https://wa.me/${whatsappNumber}`}
                        className="btn whatsapp-button"
                        target="_blank"
                        rel="noopener noreferrer">
                        Contáctanos por WhatsApp
                    </a>
                    <p className="whatsapp-text">Nuestro número de WhatsApp: {whatsappNumber}</p>
                </div>
            </div>
            <footer className="developers">
                <h3>Desarrolladores del Sitio Web</h3>
                <ul>
                    <li>Emmanuel Valenzuela</li>
                    <li>Vicente Manriquez</li>
                </ul>
            </footer>
        </>
    );
}