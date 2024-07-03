  START    100
A    DS    3
L1    MOVEM    AREG,    B
    ADD    AREG,    C
    MOVER    AREG,    ='12'
D    EQU    A+1
    LTORG
L2    PRINT    D
    ORIGIN     A-1
    MOVER    AREG,    ='5'
C     DC     '5'
    ORIGIN    L2+1
    STOP
B    DC    '19'
    END        