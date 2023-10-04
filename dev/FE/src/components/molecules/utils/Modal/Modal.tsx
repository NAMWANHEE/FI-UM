import { convertClassName, convertClassNameList } from '@/utils';
import styles from './Modal.module.scss';

interface ModalProps {
  className?: string;
  isOpen: boolean;
  toggle: () => void;
  scrollTop?: number;
  children: JSX.Element;
  aiModal?: boolean;
}

const Modal = ({
  className,
  toggle,
  isOpen,
  scrollTop,
  children,
  aiModal,
}: ModalProps): JSX.Element => {
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
            className={convertClassNameList(
              aiModal ?? false ? styles['modal-aiBox'] : styles['modal-box'],
            )}
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
