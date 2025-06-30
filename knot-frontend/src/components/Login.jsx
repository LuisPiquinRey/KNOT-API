import '../css/Login.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEyeSlash, faUser } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';
import axios from 'axios';

export function Login(){

    const [token,setToken]=useState('');
    const[dataUser,setDataUser]=useState({
        username: '',
        password: ''
    });
    const handleChange= (event)=>{
        setDataUser({
            ...dataUser,
            [event.target.name]: event.target.value,
        });
    };
    const handleSubmit= (target)=>{
        target.preventDefault();
        axios
            .post("http://localhost:8090/signIn",dataUser)
            .then((response)=>{
                if(response.status !== 200){
                    alert('Error with login');
                    return;
                }else{
                    localStorage.setItem('token', response.data);
                    alert('Login successful');
                    setToken(response.data);
                }
            })
            .catch((err)=>{
                alert('Error with token jwt');
            })
    }
    return(
        <div className='blocklogin'>
            <h1 id='titlelogin'>Login</h1>
            <div id='contentlogin' className='blocklogin'>
                <div className='circle'></div>
                <div className='circle'></div>
                <div id='icon-user'>
                    <FontAwesomeIcon icon={faUser} id='icon'></FontAwesomeIcon>
                </div>
                <form id='formlogin'  onSubmit={handleSubmit}>
                    <input className='inputlogin' type='text' placeholder='Username' required onChange={handleChange} value={dataUser.username} name='username'/>
                    <div className='input-group'>
                        <input className='inputlogin' type='password' placeholder='Password' required onChange={handleChange} value={dataUser.password} name='password'/>
                        <FontAwesomeIcon icon={faEyeSlash} id='icon-password'/>
                    </div>
                    <div style={{display: 'flex', justifyContent: 'flex-end', width: '100%', alignItems: 'center', position: 'relative',
                        top: '-15px'
                    }}>
                        <h3>Forget password?</h3>
                        <input id='submitlogin' type='submit' value={'Submit'}></input>
                    </div>
                    <div className='circle bubble-login'></div>
                </form>
            </div>
        </div>
    );
}