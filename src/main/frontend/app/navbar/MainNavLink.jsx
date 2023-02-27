'use-client'
import Link from "next/link"
export default function MainNavLink({path, text, isActive}) {
    return (
        <li>
            <Link href={path} className={`block py-2 pl-3 pr-4 rounded md:bg-transparent md:p-0 ${isActive ? 'text-blue-700 ': 'text-gray-400 hover:text-secondary'}`}>{text}</Link>
        </li>
    )
}