'use client'
import {CiEdit} from 'react-icons/ci'

export default function UserDetailsView({toggle, user}){
    const formatPhone = (phoneNumber) => {
        const cleaned = ('' + phoneNumber).replace(/\D/g, '');
        const match = cleaned.match(/^(\d{3})(\d{3})(\d{4})$/);
        return match ? '(' + match[1] + ') ' + match[2] + '-' + match[3] : null;
    }
    return (
        <>
                {/* User account details read-only display */}
                <div className='flex pb-2 mb-3 border-b border-secondary w-full'>
                    <h1 className='text-2xl font-display mr-auto mt-auto text-white'>Account Details</h1>
                    <CiEdit
                        id='edit-icon-button' 
                        className='transition-all text-primary ease-in ml-auto hover:scale-110 hover:fill-slate-300 md:hidden' 
                        size={30} 
                        role='button' 
                        aria-label='Edit' 
                        onClick={toggle}
                    />
                    
                </div>
                {/* Read-only details */}
                <div className='mr-auto'>
                    <p className='py-3 text-sm font-body text-white'><span className='text-slate-300'>Email: </span>{user.email}</p>
                    <p className='py-3 text-sm font-body text-white'><span className='text-slate-300'>Name: </span>{user.firstName} {user.lastName}</p>
                    <p className='py-3 text-sm font-body text-white'><span className='text-slate-300'>Phone: </span>{formatPhone(user.phone)}</p>
                    <button id='edit-button'
                        className='transition-all ease-in hidden ml-auto px-4 py-1 m-1 rounded font-bold font-body bg-transparent 
                                text-primary border border-primary hover:bg-primary hover:text-white hover:scale-110 md:block'
                        onClick={toggle}>
                    Edit
                    </button>
                </div>
            </>
    )
}