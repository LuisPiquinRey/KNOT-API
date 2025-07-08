
import '../css/UnauthorizedPage.css'

export default function UnauthorizedPage() {
    return (
        <>
            <div className="unauthorized-page-wrapper">
                <div className="block-unauthorized">
                    <img src='/resources/Padlock.svg' className='black-icon'/>
                    <h1>Unauthorized</h1>
                    <p>You do not have permission to view this page.</p>
                    <button className='input-knot style-knot submit-knot'>Go back to login</button>
                </div>
            </div>
        </>
    );
}