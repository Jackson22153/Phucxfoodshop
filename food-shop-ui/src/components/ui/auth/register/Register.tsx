import { useState } from "react";
import { LOGIN_AUTH } from "../../../../constant/FoodShoppingURL";
import { RegisterInfo } from "../../../../model/Type";
import { registerCustomer } from "../../../../api/AuthorizationApi";
import { getEmailSentIcon } from "../../../../service/Image";
import { Link } from "react-router-dom";

export default function RegisterComponent(){
    const [registerInfo, setRegisterInfo] = useState<RegisterInfo>({
        firstname: "",
        lastname: "",
        username: "",
        password: "",
        confirmPassword: "",
        email: ""
    })
    const [emailIsShowed, setEmailIsShowed] = useState(false)
    const emailicon = getEmailSentIcon();
    const [error, setError] = useState("")

    const onChange = (e)=>{
        const name = e.target.name;
        const value = e.target.value;
        setRegisterInfo({...registerInfo, [name]:value})
    }

    const onClickRegister = async (e)=>{
        e.preventDefault();
        try {
            const res = await registerCustomer(registerInfo);
            if(200<=res.status&& res.status<300){
                setEmailIsShowed(isShowed => !isShowed);
            }
        } catch (error) {
            setError(error.response.data.error)
        }
        
    }


    return(
        <div className="container">
            <div className="center">
                <h1>Register</h1>
                {error.length>0 &&
                    <div className="invalid-feedback d-block text-align-center">
                        {error}
                    </div>
                }
                <form method="POST" action="" onSubmit={onClickRegister}>
                    <div className="txt_field">
                        <input type="text" name="firstname" required onChange={onChange} value={registerInfo.firstname}/>
                        <span></span>
                        <label htmlFor="firstname">First name</label>
                    </div>
                    <div className="txt_field">
                        <input type="text" name="lastname" required onChange={onChange} value={registerInfo.lastname}/>
                        <span></span>
                        <label htmlFor="lastname">Last name</label>
                    </div>
                    <div className="txt_field">
                        <input type="email" name="email" required onChange={onChange} value={registerInfo.email}/>
                        <span></span>
                        <label htmlFor="email">Email</label>
                    </div>
                    <div className="txt_field">
                        <input type="text" name="username" required onChange={onChange} value={registerInfo.username}/>
                        <span></span>
                        <label htmlFor="username">Username</label>
                    </div>
                    <div className="txt_field">
                        <input type="password" name="password" required onChange={onChange} value={registerInfo.password}/>
                        <span></span>
                        <label htmlFor="password">Password</label>
                    </div>
                    <div className="txt_field">
                        <input type="password" name="confirmPassword" required onChange={onChange} value={registerInfo.confirmPassword}/>
                        <span></span>
                        <label htmlFor="confirmPassword">Confirm Password</label>
                    </div>
                    <input type="submit" value="Sign Up" />
                    <div className="signup_link">
                        Have an Account ? <Link to={LOGIN_AUTH}>Login Here</Link>
                    </div>
                </form>
            </div>
            {emailIsShowed &&
                <div className="modal bg-blur d-block" tabIndex={-1} id="verification-email-modal" role="dialog">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-body">
                                <div className="w-100 d-flex justify-content-center">
                                    <header>
                                        <img className="w-100" src={emailicon} alt="" />
                                    </header>
                                </div>
                                <h4 className="text-align-center mt-4"><b>Verify your email</b></h4>
                                <p className="text-align-center mt-4">You have entered <b>{registerInfo.email}</b> as the email address for your account</p>
                                <p className="text-align-center">Please verify this email address</p> 
                            </div>
                        </div>
                    </div>
                </div>
            }
        </div>
    )
}