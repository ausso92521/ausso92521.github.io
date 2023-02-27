'use client'
import { useState } from 'react';
import UserDetailsEdit from './UserDetailsEdit';
import UserDetailsView from './UserDetailsView';

export default function UserDetails({user}) {
    const [editMode, setEditMode] = useState(false);

    const handleEditToggle = () => {
        setEditMode(!editMode);
    }

    return(
        <div key={user.id} className='text-xl transition-all ease-in bg-slate-800 w-full h-fit mr-auto py-8 px-6 rounded-md shadow shadow-secondary md:w-2/5'>
        {
        editMode ? 
            <UserDetailsEdit user={user} toggle={handleEditToggle}/>
            :
            <UserDetailsView user={user} toggle={handleEditToggle} />
        }
        </div>
    )
}