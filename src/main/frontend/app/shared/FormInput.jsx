'use client'

export default function UserDetailsEditFormInput({name, text, type, placeholder, required = false}){
    return(
    <div className='border-b border-slate-500 flex flex-col'>
        <label htmlFor={name} className='text-white font-display'>{text}</label>
        <input autoComplete='new-password' type={type} name={name} id={name} placeholder={placeholder} 
            className='px-2 mb-3 rounded-lg h-12 shadow-inner caret-secondary border bg-slate-500 border-gray-300
            hover:shadow-primary/30 focus:outline-none text-white placeholder:text-slate-300
            focus:border-2 focus:border-primary md:rounded-lg md:h-fit'
            required={required ? true : false}/>
    </div>
    )
}