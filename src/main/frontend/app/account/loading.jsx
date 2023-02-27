import { BarLoader } from "react-spinners";

export default function Loading() {
    return (
        <div className="flex w-full h-screen">
                <BarLoader className="m-auto" color="#FFB74D"/>
        </div>
    )
}