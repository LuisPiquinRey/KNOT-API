import '../css/Login.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import { useAuth } from './AuthContext';
import { useNavigate } from 'react-router-dom';

export function Login() {
    const [token, setToken] = useState('');
    const { login }=useAuth();
    const navigate = useNavigate();
    const [dataUser, setDataUser] = useState({
        username: '',
        password: ''
    });
    const[eyeActive,setEyeActive]=useState(false);

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
                login (response.data.token, decodedHeader.role, dataUser);

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
                        <input type='text' className='input-knot style-knot' placeholder='Username' name='username' value={dataUser.username}  onChange={handleChange}/>
                        <div className='input-group-knot'>
                            <input required type={eyeActive ? 'text' : 'password'}  className='input-knot style-knot' placeholder='Password' name='password' value={dataUser.password}   onChange={handleChange}/>
                            <img id='eye-icon' src={eyeActive ? 'public/resources/visibility-on.svg' : 'public/resources/visibility-off.svg'}  onClick={handlerEye}/>
                        </div>
                        <input type='submit' required className='input-knot style-knot' id='submit-knot' value='Sign in'/>
                    </form>
                    <a href="#" className='forgot-password'>Forgot your password?</a>
                    <a href="#" id='account-text-knot'>Don´t have a account?</a>
                    <button className='button-knot style-knot'>Create new account</button>
                </div>
            </div>
        </>
    );
}
