'use client'
import { useState, useEffect } from 'react';
import api from '../../../lib/axios';
import AnimalListItemCompact from './AnimalListItemCompact';
import AnimalModal from './AnimalModal';

export default function AnimalListCompact({userId}) {
    const [animals, setAnimals] = useState([]);

    const addAnimal = (newAnimal) => {
        setAnimals((prevAnimals) => [...prevAnimals, newAnimal]);
    }

    const getAnimals = async () => {
        return await userId ?
            api.get('/animals?ownerId=' + userId)
            :
            api.get('/animals');
    }

    useEffect(() => {
        getAnimals().then(res => {
            setAnimals(res.data)
        })
    }, [])

    return (
        <div className='flex flex-col text-xl transition-all ease-in bg-slate-800 w-full py-8 rounded-md shadow shadow-secondary h-[25rem]'>
            <div className='flex justify-between mx-6 pr-2 pb-2 mb-3 border-b border-secondary'>
                <div>
                    <span className='w-fit text-2xl font-display  mt-auto text-white'>Animals</span>
                </div>
                <div>
                    <AnimalModal addAnimal={addAnimal} userId={userId}/>
                </div>
            </div>
            <div className='overflow-y-auto px-6'>
            {
                animals ?
                    animals.map(animal => (
                        <AnimalListItemCompact key={animal.id} animal={animal} />
                    ))
                :
                    null
            }
            </div>
        </div>
    )
}