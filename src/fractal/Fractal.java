package fractal;

import math.Complex;
/** Интерфейс, описывающий фрактал
 * */
public interface Fractal {
    /** Метод, проверяющий принадлежность точки множеству фрактала
     * @param c проверяемая точка
     * @return true если принадлежит, иначе false
     * */
    float isInSet(Complex c);
}
