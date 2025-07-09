import '../css/Login.css'
import '../css/Common.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { useAuth } from './AuthContext';
import { useNavigate } from 'react-router-dom';
import { signInWithPopup } from 'firebase/auth';
import { auth_firebase, providerGithub } from '../firebaseconfig/config';

export function Login() {
    const [token, setToken] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();
    const [dataUser, setDataUser] = useState({
        username: '',
        password: ''
    });
    const [eyeActive, setEyeActive] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleChange = (event) => {
        setDataUser({
            ...dataUser,
            [event.target.name]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        axios
            .post("http://localhost:8090/user/login", dataUser)
            .then((response) => {
                localStorage.setItem('token', response.data.token);
                alert('Login successful');
                setToken(response.data);
                const decodedHeader = jwtDecode(response.data.token);
                console.log(decodedHeader);
                login(response.data.token, decodedHeader.role, dataUser);

                if (decodedHeader.role === 'ADMIN' || decodedHeader.role === 'SUPER_ADMIN') {
                    console.log('Redirecting to /admin');
                    navigate('/admin');
                } else {
                    console.log('Redirecting to /');
                    navigate('/');
                }
            })
            .catch(() => {
                alert('Error with token jwt');
            });
    };

    const handlerEye = () => {
        setEyeActive(!eyeActive);
    }

    async function signInGithub(){
        setLoading(true);
        console.log('Login with GitHub...');
        signInWithPopup(auth_firebase, providerGithub)
            .then((result) => {
                const user = result.user;
                console.log('User authenticated: ', user);
            })
            .catch((error) => {
                console.error(error);
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <>
            <div className='header-block'>
                <img id='icon-knot-superior' src='public/resources/Knot_icon_text.png'/>
            </div>
            <div className='block-body'>
                <div className='sign-in-block'>
                    <img id='icon-knot' src='public/resources/Knot_icon.png' alt='Knot icon'/>
                    <h1 id='title'>Sign in</h1>
                    <form onSubmit={handleSubmit}>
                        <input type='text' className='input-knot style-knot' placeholder='Username' name='username' value={dataUser.username} onChange={handleChange}/>
                        <div className='input-group-knot'>
                            <input required type={eyeActive ? 'text' : 'password'} className='input-knot style-knot' placeholder='Password' name='password' value={dataUser.password} onChange={handleChange}/>
                            <img id='eye-icon' src={eyeActive ? 'public/resources/visibility-on.svg' : 'public/resources/visibility-off.svg'} onClick={handlerEye}/>
                        </div>
                        <input type='submit' required className='input-knot style-knot submit-knot' value='Sign in'/>
                    </form>
                    <a href="#" id='account-text-knot'>Don´t have a account?</a>
                    <button className='button-knot style-knot'>Create new account</button>
                    <button 
                        onClick={signInGithub} 
                        disabled={loading}
                        className='signin-knot'
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" height="100" width="100" viewBox="0 0 496 512"><path d="M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3 .3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5 .3-6.2 2.3zm44.2-1.7c-2.9 .7-4.9 2.6-4.6 4.9 .3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3 .7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3 .3 2.9 2.3 3.9 1.6 1 3.6 .7 4.3-.7 .7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3 .7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3 .7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z"/></svg>
                    </button>
                </div>
            </div>
        </>
    );
}