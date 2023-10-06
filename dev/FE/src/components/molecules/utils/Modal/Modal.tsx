import { convertClassName, convertClassNameList } from '@/utils';
import styles from './Modal.module.scss';

interface ModalProps {
  className?: string;
  isOpen: boolean;
  toggle: () => void;
  scrollTop?: number;
  children: JSX.Element;
  aiModal?: boolean;
  auctionModal?: boolean;
}

const Modal = ({
  className,
  toggle,
  isOpen,
  scrollTop,
  children,
  aiModal,
  auctionModal,
}: ModalProps): JSX.Element => {
  const checkModal = () => {
    if (aiModal) {
      return styles['modal-aiBox'];
    }
    if (auctionModal) {
      return styles['modal-auctionBox'];
    }
    return styles['modal-box'];
  };

  return (
    <>
      {isOpen && (
        <div
          style={{ top: scrollTop }}
          className={convertClassNameList(
            convertClassName(className, styles),
            styles['modal-overlay'],
          )}
          onClick={(e) => {
            e.stopPropagation();
            toggle();
          }}
        >
          <div
            className={convertClassNameList(checkModal())}
            onClick={(e) => e.stopPropagation()}
          >
            {children}
          </div>
        </div>
      )}
    </>
  );
};

export default Modal;
