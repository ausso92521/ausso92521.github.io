'use client'
import api from '../../../lib/axios';
import { TbX } from 'react-icons/tb';
import { useRouter } from 'next/navigation';
import { useState } from 'react'
import FormInput from '../../shared/FormInput';
import FormPasswordInput from '../../shared/FormPasswordInput';

export default function UserDetailsEdit({toggle, user}) {
    const [error, setError] = useState(null);
    const router = useRouter();

    const handleSubmitChanges = async (event) => {
        console.log('inside handlesubmit')
        event.preventDefault();
        setError(null);
        const data = {
            email: event.target.email.value || null,
            firstName: event.target.firstName.value || null,
            lastName: event.target.lastName.value || null,
            phone: event.target.phone.value.replace(/\D/g, '') || null,
            currentPassword: event.target.currentPassword.value || null,
            newPassword: event.target.newPassword.value || null
        };
        const confirmPassword = event.target.confirmNewPassword.value || null;

        if(data.newPassword !== confirmPassword){
            setError('New password mismatch. Check your input and try again.');
            return;
        }

        await api.patch('/users/' + user.id, 
        data, {
            headers: { 
              "Content-Type": "application/json"
            }
          }).then(res => {
            if(res.status === 200){
                router.push('/logout');
            }
          }).catch(error => {
            if(error.response.status === 401){
                router.push('/login');
            }
            setError(error.response.data.detail)
          })
    }

    return (
        <>
            {/* User account details edit mode display */}
            <div className='flex pb-2 mb-3 border-b border-secondary w-full'>
                
                <div className='flex flex-col'>
                    <h1 className='text-2xl font-display mr-auto mt-auto text-white w-full'>Edit Details</h1>
                    <p className='text-white text-xs font-body'>Only edit the values you would like to update.</p>
                </div>
                <TbX 
                    id='close-icon-button'
                    className='transition-all ease-in text-primary ml-auto hover:scale-110' 
                    color='primary' 
                    size={40} 
                    role='button' 
                    aria-label='Close' 
                    onClick={toggle} />
                    
            </div>
            {
                    error ?
                        <div className='flex flex-row text-sm bg-slate-700 text-secondary font-body'>
                            <p>Unable to update your account. Please try again. {error}</p>
                        </div>
                        :
                        null
                }

            {/* Form User can change their account details with */}
            <form className='flex flex-col w-11/12 mx-auto' onSubmit={handleSubmitChanges}>
                    <FormInput name='email' text='Email' type='email' placeholder={user.email} />
                    <FormInput name='firstName' text='First Name' type='text' placeholder={user.firstName} />
                    <FormInput name='lastName' text='Last Name' type='text' placeholder={user.lastName} />
                    <FormInput name='phone' text='Phone' type='text' placeholder={user.phone} />
                    <FormPasswordInput name='newPassword' text='New Password' />
                    <FormPasswordInput name='confirmNewPassword' text='Confirm New Password' />
                    <button type='submit' className="bg-secondary text-white font-bold w-20 p-2 m-2 self-end rounded-md">Submit</button>
            </form>    
            </>   
    )
}