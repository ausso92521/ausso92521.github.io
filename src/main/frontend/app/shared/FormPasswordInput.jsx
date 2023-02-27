'use client'
import { useState } from 'react';
import {RxEyeOpen, RxEyeClosed} from 'react-icons/rx';

export default function UserDetailsEditFormPasswordInput({name, text}){
    const [show, setShow] = useState(false);

    const toggleShow = () => {
        setShow(!show);
    }

    return(
    <div className='relative border-b border-slate-500 flex flex-col'>
        <label htmlFor={name} className='text-white font-display'>{text}</label>
        <input autoComplete='new-password' type={show ? 'text' : 'password'} name={name} id={name}
            className='px-2 mb-3 rounded-lg h-12 shadow-inner caret-secondary border bg-slate-500 border-gray-300
            hover:shadow-primary/30 focus:outline-none text-white placeholder:text-slate-300
            focus:border-2 focus:border-primary md:rounded-lg md:h-fit' />
            {
                show ?
                    <RxEyeOpen role='button' className='absolute top-1/2 right-3' onClick={() => toggleShow()} />
                :
                    <RxEyeClosed role='button' className='absolute top-1/2 right-3' onClick={() => toggleShow()} />
            }
        
    </div>
    )
}