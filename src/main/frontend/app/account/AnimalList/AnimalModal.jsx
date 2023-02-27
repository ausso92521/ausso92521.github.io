'use client'
import {useState} from 'react';
import api from '../../../lib/axios';
import FormInput from '../../shared/FormInput';
import {ConvertToLocalDateTime} from '../../../lib/DateUtil';

export default function AnimalModal({userId, addAnimal}){
    const [showModal, setShowModal] = useState(false);

    const saveAnimal = async (event) => {
      event.preventDefault();
      const date = new Date();
      const options = { timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone};
      const localDate = ConvertToLocalDateTime(date.toLocaleString('en-US', options));
      const formDate = event.target.dob.value;

      const data = {
        name: event.target.name.value || null,
        dateOfBirth: formDate.length === 0 ? localDate : formDate,
        owner: {
          id: userId
        }
      }

      await api.post('/animals',
      data, {
        headers: { 
          "Content-Type": "application/json"
        }
      }).then(res => {
        if(res.status === 201){
          addAnimal(res.data);
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
                        <div className="relative w-auto my-6 mx-auto max-w-3xl">
                          {/*content*/}
                          <div className="border-0 rounded-lg shadow-lg relative flex flex-col w-full bg-slate-800 outline-none focus:outline-none">
                            {/*header*/}
                            <div className="flex items-start justify-between p-5 border-b border-solid border-secondary rounded-t">
                              <h3 className="text-3xl font-semibold text-white font-display">
                                New Animal
                              </h3>
                            </div>
                            <form onSubmit={saveAnimal}>                           
                            {/*body*/}
                            <div className="relative p-6 flex-auto">
                              <FormInput name='name' text="Pet's Name" type='text' placeholder='Name' required={true}/>
                              <FormInput name='dob' text="Pet's Date of Birth" type='datetime-local' placeholder={null} />
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
