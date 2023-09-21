import { Payment } from '@/types/payment';
import { authApi } from '.';

const getTossPay = async (payment: Payment): Promise<void> => {
  return await authApi.get(`toss/success`, { params: payment });
};

export { getTossPay };
