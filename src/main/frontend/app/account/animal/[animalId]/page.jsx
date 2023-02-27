'use client'
import {useEffect, useState} from 'react';
import api from '../../../../lib/axios';
import { CalculateAge } from '../../../../lib/DateUtil';
import Note from './Note';
import {useRouter} from 'next/navigation';
import NoteModal from './NoteModal';

export default function Animal(context){
    const router = useRouter();
    const [animal, setAnimal] = useState();
    const [notes, setNotes] = useState();

    const animalId = context.params.animalId;

    

    const getAnimal = async () => {
        return await api.get(`/animals/${animalId}`)
            .then(res => {
                setAnimal(res.data);
                setNotes(res.data.notes);
            })
            .catch(error => {
                if(error.response){
                    if(error.response.status === 401){
                        localStorage.clear();
                        router.push('/login');
                    }
                    setError(error.response.data.detail);
                }
            });   
    }

    const deleteNote = async (id) => {
        return await api.delete(`/notes/${id}`)
            .then(res => 
                setNotes(notes.filter(note => note.id !== id))
            )
            .catch(error => {
                if(error.response){
                    if(error.response.status === 401){
                        localStorage.clear();
                        router.push('/login');
                    }
                    setError(error.response.data.detail);
                }
            })
    }

    const handleAddNote = (newNote) => {
        setNotes((prevNotes) => [...prevNotes, newNote]);
    }

    useEffect(()=>{
        getAnimal();
    }, []);

    const removeNote = (id) => {
        deleteNote(id);
    }

    return (
        animal ? (
            <div className='flex flex-col w-full bg-slate-800 text-white rounded shadow shadow-secondary md:flex-row'>
                <div id='animalInfo' className='mx-3 py-3 px-4 border-b border-secondary md:border-b-0 md:p-3 md:float-left md:w-1/5 md:border-r md:border-r-secondary md:text-center md:my-3'>
                    <p className='font-display text-xl float-left'>{animal.name}</p>
                    <p className='font-display text-md float-right'>Age: {CalculateAge(animal.dateOfBirth)}</p>
                </div>
                <div id='notes' className='flex flex-col py-2 px-4 h-[25rem] font-body overflow-y-auto md:p-3 md:float-right md:w-4/5'>
                    <div id='addButtonHolder' className='flex flex-row pr-5 mb-5 w-full h-11 justify-end'>
                        <NoteModal className='ml-auto' addNote={handleAddNote} animalId={animal.id}/>
                    </div>
                    {
                        notes.length > 0 ? (
                            <div className='flex flex-col'>
                                {
                                    notes.map(note => (
                                        <Note key={note.id} note={note} handleDelete={removeNote} animalId={animal.id} />
                                    ))
                                }
                            </div>
                        )
                        :
                        (
                            <div className='flex flex-row w-full'>
                                <p className='font-display text-3xl text-white m-auto'>No Notes Yet - Try Adding Some!</p>
                            </div>
                        )
                    }
                </div>
            </div>
        )
        :
        null
    )
}