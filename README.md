Decision making
============

Automatic decision system.

decision-making-toolkit
-------------------------

- maxi max
- maxi min
- hurwicz
- savage
- laplace
- maximum expected value
- maximum expected regret
- bayesian
- YAML file loader

decision-making-console
-------------------------

Allow to run toolkit directly from console.

Example
-------------------------

**YAML file with decision matrix**

    alternatives:
    - name: "I take an umbrella"
      risk: 0.9
    - name: "I don't take an umbrella"
      risk: 0.5
    scenes:
    - name: "it rains"
      probability: 0.4
    - name: "it doesn't rain"
      probability: 0.6
    outputs:
    - name: "dry clothes, heavy bag"
      value: 15.0
    - name: "dry clothes, heavy bag"
      value: 15.0
    - name: "wet clothes, lightweight bag"
      value: 0.0
    - name: "dry clothes, lightweight bag"
      value: 18.0
    factors:
    - factorName: "pressure"
      factorsNames:
      - "stable low"
      - "stable high"
      - "variable"
      chosenFactor: "stable low"
      factorsOutputsValues:
      - 0.6
      - 0.3
      - 0.02
      - 0.8
      - 0.5
      - 0.4
      
**Program output**

    +-----------------------------+---------------------------------+----------------------------------+   
    |alternatives \ scenes        |it rains(0.4)                    |it doesn't rain(0.6)              |    
    +-----------------------------+---------------------------------+----------------------------------+    
    |I take an umbrella(0.9)      |dry clothes, heavy bag(15.0)     |dry clothes, heavy bag(15.0)      |   
    +-----------------------------+---------------------------------+----------------------------------+   
    |I don't take an umbrella(0.5)|wet clothes, lightweight bag(0.0)|dry clothes, lightweight bag(18.0)|   
    +--------------------------------------------------------------------------------------------------+  
    |                                             pressure                                             |   
    +--------------------------------------------------------------------------------------------------+    
    |<stable low>                 |0.6                              |0.3                               |   
    +-----------------------------+---------------------------------+----------------------------------+
    |stable high                  |0.02                             |0.8                               |
    +-----------------------------+---------------------------------+----------------------------------+ 
    |variable                     |0.5                              |0.4                               |  
    +-----------------------------+---------------------------------+----------------------------------+
    Maxi max: I don't take an umbrella
    Maxi min: I take an umbrella
    Hurwicz: I take an umbrella
    Savage: I take an umbrella
    Laplace: I take an umbrella
    Maximum expected value: I take an umbrella
    Maximum expected regret: I don't take an umbrella
    Bayesian: I take an umbrella