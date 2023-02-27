'use client'
import api from '../../lib/axios';
import { useState, useEffect } from 'react';
import WelcomeTagline from './WelcomeTagline';
import UserDetails from './UserDetails/UserDetails';
import AnimalListCompact from './AnimalList/AnimalListCompact';
import { useRouter } from 'next/navigation';



export default function Account(){
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);
    const router = useRouter();

    const getUser = async () => {
        return await api.get('/users/self')
        .then(res => setUser(res.data))
        .catch(error => {
            if(error.response.status === 401){
                localStorage.clear();
                router.push('/login');
            }
            setError(error.response.data.detail);
        });       
    }

    useEffect(() => {
        getUser();
    }, [])
    
    return (
        <main className='w-full'>
            {
                error ?
                (
                    <div className='flex flex-col w-full h-full'>
                            <h1 className='m-auto'>Something went wrong</h1>
                            <h2 className='m-auto'>{error}</h2>
                    </div>
                )
                :
                null
            }
            {user ? 
            (
            <>
                <div className='mb-3 pl-3'>
                    <WelcomeTagline user={user}/>
                </div>
                <div  className='flex flex-col space-y-4 flex-grow w-full md:space-y-0 md:flex-row md:space-x-6'>
                    <UserDetails user={user}/>
                    <AnimalListCompact userId={user.id} />
                </div>
            </>
            ): null}
        </main>
        
    )
}