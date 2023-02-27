'use client'
import axios from "../../lib/axios";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function Logout(){
    const router = useRouter();
    const logout = async () => {
        const response = await axios.delete("https://localhost:443/api/v1/logout");

        if(response.status == 204){
            localStorage.clear();
            router.push('/login');
        }
    }

    useEffect(() => {
        logout();
    }, []);
}