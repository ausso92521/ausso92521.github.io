'use client'
import api from "../../lib/axios";
import { useRouter } from 'next/navigation';
import { useState } from "react";

export default function LoginPage(){
    const [error, setError] = useState(null);
    const router = useRouter();

    const handleLogin = async (event) => {
        event.preventDefault();
        setError(null);

        const data = {
            email: event.target.email.value,
            password: event.target.password.value
        };

        await api.post('/login', 
        data, {
            headers: { 
              "Content-Type": "application/x-www-form-urlencoded"
            }
          })
          .then(() => {
            localStorage.setItem('login_status', true);
            router.push('/account');
          })
          .catch(error => {
            setError(error.response.data.detail);
          })

    }

    return(
        <div className='transition-all ease-in w-11/12 mx-auto bg-slate-700 text-white font-body h-fit p-6 rounded-md shadow shadow-secondary sm:w-10/12 md:w-9/12 lg:w-2/3'>
            {
                error ? (
                    <div className='flex font-body font-bold bg-slate-700 text-white border border-accent'>
                        <h1 className='m-auto'>{error}</h1>
                    </div>
                )
                :
                null
            }
            <form className="flex flex-col" onSubmit={handleLogin}>
                <h1 className="font-bold font-display text-2xl self-center shadow-sm">Login</h1>
                
                <label className="hidden font-display md:block" htmlFor='email'>Email</label>
                <input type='email' 
                        name='email' 
                        id='email' 
                        placeholder="Email" 
                        className="px-2 mb-3 rounded-full caret-secondary shadow-inner border bg-slate-500 border-gray-300
                                  hover:shadow-primary/30 focus:outline-none 
                                    focus:border-2 focus:border-primary
                                    md:rounded-lg md:placeholder-transparent" 
                        required
                />
                
                <label className="hidden font-display md:block" htmlFor='password'>Password</label>
                <input type='password' 
                        name='password' 
                        id='password' 
                        placeholder="Password" 
                        className="px-2 mb-3 rounded-full caret-secondary shadow-inner border bg-slate-500 border-gray-300
                                  hover:shadow-primary/30 focus:outline-none
                                    focus:border-2 focus:border-primary
                                    md:rounded-lg md:placeholder-transparent"
                        required
                />

                <button type='submit' className="tranisiton-all ease-in border border-secondary text-secondary font-bold w-20 p-2 self-end rounded-md
                                                  hover:text-white hover:border-none hover:bg-secondary hover:scale-110 active:scale-105">
                  Submit
                </button>
            </form>
        </div>
    )
}