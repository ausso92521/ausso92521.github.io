import './globals.css';
import MainNavBar from './navbar/MainNavBar'
import Footer from './footer/Footer'

export default function Layout({children}){
    return (
        <html lang="en">
            <head>
                <title>Animal Tracker</title>
            </head>
            <body>
                <div className='flex flex-col h-screen justify-between'>
                    <MainNavBar />
                    <div id='content' className='w-full px-2 py-2 md:py-2 md:px-16'>
                        {children}
                    </div>
                    <Footer />
                </div>
            </body>
        </html>
    )
}