'use client'

import Link from "next/link"

export default function AnimalListItemCompact({animal}) {

    return (
        <Link href={`/account/animal/${animal.id}`}>
            <div className='flex-row items-stretch flow-root bg-slate-700 mt-3 rounded-lg py-3 px-2 hover:scale-105 md:hover:scale-[1.02] hover:bg-slate-600'
                role="button" >
                <div className="flex float-left pr-2 w-1/4 h-full">
                    <h1 className="font-display text-lg w-fit my-auto text-slate-400">{animal.name}</h1>
                </div>
                <div className="float-right pl-2 text-sm border-l border-slate-300 ml-auto w-3/5 align-middle">
                    <div>
                        <p className="text-white font-body h-full">Birthday: {new Date(animal.dateOfBirth).toDateString()}</p>
                    </div>
                    <div>
                        <span className="text-white font-body">Note count: {animal.notes ? animal.notes.length : 0}</span>
                    </div>
                </div>
            </div>
        </Link>
    )
}