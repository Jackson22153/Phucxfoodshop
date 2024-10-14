import { MouseEventHandler } from "react";
import { Modal } from "../../../../model/WebType";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faXmark } from "@fortawesome/free-solid-svg-icons";

interface Props{
    modal: Modal,
    confirmText?: string,
    closeText?: string,
    handleCloseButton: any,
    handleConfirmButton: any
}
export default function ModalComponent(prop: Props){
    const modal = prop.modal;
    // const title = modal.title;
    const message = modal.message;
    const isShowed = modal.isShowed;
    const handleCloseButton:MouseEventHandler<HTMLButtonElement>=(event)=>{
        event.preventDefault();
        prop.handleCloseButton();
    }
    const handleConfirmButton:MouseEventHandler<HTMLButtonElement>=(event)=>{
        event.preventDefault();
        prop.handleCloseButton();
        prop.handleConfirmButton();
    }

    if(isShowed){
        return(
            <div className={`modal ${isShowed?'d-block': 'fade'} screen-center`} id="confirm-modal" tabIndex={-1} role="dialog" 
                aria-labelledby="confirm-modal-label">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title text-black" id="confirm-modal-label">{modal.title}</h5>
                            <button type="button" onClick={handleCloseButton} className="close ml-auto text-bg-light" 
                                data-dismiss="modal" aria-label="Close">
                                <FontAwesomeIcon icon={faXmark} />
                            </button>
                        </div>
                        <div className="modal-body">
                            <p className="text-black">{message}</p>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal"
                                onClick={handleCloseButton}>{prop.closeText?prop.closeText:'Close'}</button>
                            <button type="button" className="btn btn-primary"
                                onClick={handleConfirmButton}>{prop.confirmText?prop.confirmText:'Confirm'}</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
    else return null;
}