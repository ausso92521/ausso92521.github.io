'use client'

export default function WelcomeTagline({user}){
    return (
        <h1 className="text-2xl font-body">Welcome, {user.firstName}!</h1>
    )
}