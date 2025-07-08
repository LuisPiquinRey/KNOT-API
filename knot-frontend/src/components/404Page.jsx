import '../css/404Page.css'
import '../css/Common.css'
export default function UnauthorizedPage() {
    return (
    <>
        <div className='header-block-404'>
            <nav id='nav-knot'>
                <div className="navbar-left">
                    <img id='icon-knot-superior' src='/resources/Knot_icon_text.png' alt='Knot Icon' />
                    <a href='#' className='navbar-item'>Inspiration</a>
                    <a href='#' className='navbar-item'>About us</a>
                </div>
                <div className="navbar-right">
                    <a href='#' className='navbar-item'>Log in</a>
                    <a href="#" className='navbar-item' id='navbar-item-signup'>Sign up</a>
                </div>
            </nav>
        </div>
        
        <main className='main-content'>
            <div className='block-body-404'>
                <div className='not-found-page'>
                    <h1><span id='not-found-title'>404</span></h1>
                    <h2>Page Not Found</h2>
                    <h3>Sorry, we can´t find the page you´re looking for.</h3>
                    <button className='button_404'>Back to Home</button>
                </div>
            </div>
        </main>
        
        <footer className='footer-404'>
            <div className='footer-links'>
                <a href="/">Home</a>
                <a href="/contact">Contact</a>
                <a href="/help">Help</a>
            </div>
            <p className='footer-text'>
                &copy; 2025 Knot Commerce. All rights reserved.
            </p>
        </footer>
    </>
    );
}