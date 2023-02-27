'use client'
export default function AccountError({error, reset}) {
    return (
        <>
            <p>This be the error page.</p>
            <h1>{error.response?.data?.message || 'Generic Error'}</h1>
        </>
    )
}