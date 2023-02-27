'use client'
import MainNavLink from "./MainNavLink";
import Link from "next/link";
import Image from "next/image";
import logo from '../../public/images/logo.svg';
import { usePathname } from "next/navigation";

export default function MainNavBar() {
const currentPath = usePathname();
const isActive = (path) => {
  return currentPath === path;
}

const loggedIn = () => {
  if(typeof window !== 'undefined'){
    return localStorage.getItem('login_status') != null;
  } else {
    return false;
  }
}

const navLinks = loggedIn() ? 
[
  {path: '/', text: 'Home'},
  {path: '/account', text: 'My PetPad'},
  {path: '/logout', text: 'Logout'},
  
]
:
[
  {path: '/', text: 'Home'},
  {path: '/login', text: 'Login'}
];

    return (
      <nav className="bg-primary border-gray-200 mb-2 px-2 py-2.5 w-full flex flex-col sm:px-4 rounded-b-xl md:px-16">
      <div className="container flex flex-wrap items-center justify-between mx-auto">
        <Link href="/" className="flex items-center">
            <Image src={logo} width={60} height={60} alt='Pet Pad Logo' className="mr-3" />
            <span className="self-center text-2xl font-display font-semibold whitespace-nowrap text-white text-shadow">Pet-Pad</span>
        </Link>
        <button data-collapse-toggle="navbar-default" type="button" className="inline-flex items-center p-2 ml-3 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600" aria-controls="navbar-default" aria-expanded="false">
          <span className="sr-only">Open main menu</span>
          <svg className="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fillRule="evenodd" d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z" clipRule="evenodd"></path></svg>
        </button>
        <div className="hidden w-full md:block md:w-auto" id="navbar-default">
          <ul className="flex flex-col p-4 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:flex-row md:space-x-7 md:mt-0 md:text-sm md:font-medium md:border-0 md:bg-white dark:bg-gray-800 md:dark:bg-gray-900 dark:border-gray-700">
            {navLinks.map(link => (
              link ?
                <MainNavLink key={link.path} path={link.path} text={link.text} isActive={isActive(link.path)} />
                :
                null
            ))}
          </ul>
        </div>
      </div>
      </nav>
    )

}