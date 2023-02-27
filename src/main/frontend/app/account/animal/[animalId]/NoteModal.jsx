'use client'
import {useState} from 'react';
import api from '../../../../lib/axios';
import FormInput from '../../../shared/FormInput';

export default function NoteModal({animalId, addNote}){
    const [showModal, setShowModal] = useState(false);

    const saveNote = async (event) => {
      event.preventDefault();

      const data = {
        content: event.target.content.value,
        animalId: animalId,
        noteDate: event.target.noteDate.value || null
      }

      await api.post('/notes',
      data, {
        headers: { 
          "Content-Type": "application/json"
        }
      }).then(res => {
        if(res.status === 201){
          addNote(res.data);
          setShowModal(false);
        }
      }).catch(err => console.error(err));
    }

    return(
        <>
            {/* <!-- Modal toggle --> */}
            <button onClick={() => setShowModal(!showModal)} 
                    className="block transition-all ease-in px-3 font-display text-4xl text-primary align-middle 
                          border border-primary rounded hover:bg-primary hover:text-white hover:scale-110" 
                    type="button">
            +
            </button>

            {
                showModal ? (
                    <>
                      <div
                        className="justify-center transition-all ease-in items-center flex overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none"
                      >
                        <div className="relative w-3/4 my-6 mx-auto max-w-3xl md:w-1/2">
                          {/*content*/}
                          <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-slate-800 outline-none focus:outline-none">
                            {/*header*/}
                            <div className="flex items-start justify-between p-5 border-b border-solid border-secondary rounded-t">
                              <h3 className="text-3xl font-semibold text-white font-display">
                                New Note
                              </h3>
                            </div>
                            <form onSubmit={saveNote}>                           
                            {/*body*/}
                            <div className="relative p-6 flex-auto">
                            <label htmlFor='content' className='text-white font-display'>Note Contents</label>
                              <textarea id='content' name='content' maxLength={3000} required rows={10}
                                        className='rounded text-body w-full caret-secondary border bg-slate-500 border-gray-300'></textarea>
                              <FormInput name='noteDate' text="Optional Date" type='datetime-local' placeholder={null} />
                            </div>
                            {/*footer*/}
                            <div className="flex items-center justify-end p-6 border-t border-solid border-secondary rounded-b">
                              <button
                                className="text-secondary background-transparent font-bold uppercase px-6 py-2 text-sm
                                             hover:text-white hover:scale-110 active:scale-105 mr-1 mb-1"
                                type="button"
                                onClick={() => setShowModal(false)}
                              >
                                Close
                              </button>
                              <button
                                className="bg-transparent text-primary border border-primary transition-all ease-in font-bold 
                                            uppercase text-sm px-6 py-3 rounded shadow mr-1 mb-1 hover:border-none hover:bg-primary
                                            hover:text-white hover:scale-110 active:scale-105"
                                type="submit">
                                Save
                              </button>
                            </div>
                            </form>
                          </div>
                        </div>
                      </div>
                      <div className="opacity-25 fixed inset-0 z-40 bg-black"></div>
                    </>
                  ) : null
            }
        </>
    )
}
