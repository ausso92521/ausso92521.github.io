'use client'

import { FormatPrettyLocalDateTime } from "../../../../lib/DateUtil"

export default function Note({note, handleDelete}){
    return(
        <div className='flex flex-col w-full py-3 border-dashed border-b'>
            <div className='flex flex-row p-5'>
                {note.content}
            </div>
            <div className='flex flex-row p-3 justify-between rounded bg-slate-700'>
                <div className="flex flex-col">
                    <div>
                        <p className='mr-auto font-display text-sm'>Created: {FormatPrettyLocalDateTime(note.creationDate)}</p>
                    </div>
                    <div>
                        {note.noteDate ? (
                            <p className='mr-auto font-display text-sm'>Event Date: {FormatPrettyLocalDateTime(note.noteDate)}</p>
                        )
                        :
                        null}
                    </div>
                </div>
            
                    <button className=" ml-auto rounded border border-primary px-3 py-2 text-primary
                                        hover:scale-110 hover:text-white hover:border-none hover:bg-primary active:scale-105"
                                        onClick={() => handleDelete(note.id)}>
                        Delete
                    </button>
            </div>
        </div>
    )
}