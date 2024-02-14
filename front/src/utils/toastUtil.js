import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

export const showToast = (message, options = {}) => {
  toast(message, {
    position: 'top-center',
    ...options,
  });
};

export const showErrorToast = (message, options = {}) => {
  toast.error(message, {
    position: 'top-center',
    ...options,
  });
};

export const showSuccessToast = (message, options = {}) => {
  toast.success(message, {
    position: 'top-center',
    ...options,
  });
};
